package com.university.universitycms.service;

import com.university.universitycms.domain.*;
import com.university.universitycms.domain.dto.LessonDTO;
import com.university.universitycms.domain.mapper.LessonMapper;
import com.university.universitycms.repository.LessonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LessonServiceTest {
    @Mock
    private LessonRepository repository;
    @Mock
    private LessonMapper lessonMapper;
    @InjectMocks
    private LessonService lessonService;

    private final static LessonDTO expectedLessonDTO = new LessonDTO();
    private final Teacher expectedTeacher = new Teacher(1L, Role.TEACHER, "Oleg", "Tot", null,
            Collections.emptySet(), null);
    private final Course expectedCourse = new Course(1L, "IT", Collections.singleton(expectedTeacher));
    private final Group expectedGroup = new Group(1L, "A12", Collections.emptySet());
    private final Lesson expectedLesson = new Lesson(null, "ENG group 4", "A108", DayOfWeek.MONDAY, LocalTime.of(1, 30),
            LocalTime.of(3, 30), expectedCourse, expectedGroup, expectedTeacher);


    @BeforeAll
    static void setUppTeacherDto(){
        expectedLessonDTO.setId(null);
        expectedLessonDTO.setName("ENG group 4");
        expectedLessonDTO.setAudience("A108");
        expectedLessonDTO.setDayOfWeek(DayOfWeek.MONDAY);
        expectedLessonDTO.setStartTime(LocalTime.of(1, 30));
        expectedLessonDTO.setEndTime(LocalTime.of(3, 30));
    }


    @Test
    void testRegisterLesson_ShouldRegisterNewLesson_WithoutThrows() {
        when(lessonMapper.lessonDTOToLesson(expectedLessonDTO)).thenReturn(expectedLesson);

        when(repository.existsAllByAudienceAndStartTimeBetweenAndDayOfWeek(expectedLesson.getAudience(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek())).thenReturn(false);
        when(repository.existsAllByGroupAndStartTimeBetweenAndDayOfWeek(expectedLesson.getGroup(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek())).thenReturn(false);
        when(repository.existsAllByTeacherAndStartTimeBetweenAndDayOfWeek(expectedLesson.getTeacher(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek())).thenReturn(false);

        lessonService.registerLesson(expectedLessonDTO);

        verify(lessonMapper).lessonDTOToLesson(expectedLessonDTO);

        verify(repository).existsAllByAudienceAndStartTimeBetweenAndDayOfWeek(expectedLesson.getAudience(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek());

        verify(repository).existsAllByGroupAndStartTimeBetweenAndDayOfWeek(expectedLesson.getGroup(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek());

        verify(repository).existsAllByTeacherAndStartTimeBetweenAndDayOfWeek(expectedLesson.getTeacher(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek());

        verify(repository).save(expectedLesson);

        verify(lessonMapper, times(1)).lessonDTOToLesson(expectedLessonDTO);

        verify(repository, times(1)).existsAllByAudienceAndStartTimeBetweenAndDayOfWeek(expectedLesson.getAudience(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek());
        verify(repository, times(1)).existsAllByGroupAndStartTimeBetweenAndDayOfWeek(expectedLesson.getGroup(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek());
        verify(repository, times(1)).existsAllByTeacherAndStartTimeBetweenAndDayOfWeek(expectedLesson.getTeacher(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek());

        verify(repository, times(1)).save(expectedLesson);


        // maybe to match verify, but why not:)
    }

    @Test
    void testRegisterLesson_AudienceNotFree_ExceptionThrown() {
        Lesson newLesson = new Lesson(null, "PE group 1", expectedLesson.getAudience(), DayOfWeek.MONDAY, LocalTime.of(1, 30),
                LocalTime.of(3, 30), null, null, null);

        when(lessonMapper.lessonDTOToLesson(expectedLessonDTO)).thenReturn(newLesson);

        when(repository.existsAllByAudienceAndStartTimeBetweenAndDayOfWeek(newLesson.getAudience(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> lessonService.registerLesson(expectedLessonDTO));

        verify(lessonMapper, times(1)).lessonDTOToLesson(expectedLessonDTO);

        verify(repository, times(1)).existsAllByAudienceAndStartTimeBetweenAndDayOfWeek(newLesson.getAudience(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek());
    }

    @Test
    void testRegisterLesson_GroupNotFree_ExceptionThrow(){
        Lesson newLesson = new Lesson(null, "PE group 1", "SLS", DayOfWeek.MONDAY, LocalTime.of(1, 30),
                LocalTime.of(3, 30), null, expectedGroup, null);

        when(lessonMapper.lessonDTOToLesson(expectedLessonDTO)).thenReturn(newLesson);

        when(repository.existsAllByGroupAndStartTimeBetweenAndDayOfWeek(newLesson.getGroup(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> lessonService.registerLesson(expectedLessonDTO));

        verify(lessonMapper, times(1)).lessonDTOToLesson(expectedLessonDTO);

        verify(repository, times(1)).existsAllByGroupAndStartTimeBetweenAndDayOfWeek(newLesson.getGroup(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek());
    }

    @Test
    void testRegisterLesson_TeacherNotFree_ExceptionThrow(){
        Lesson newLesson = new Lesson(null, "PE group 1", "AMG", DayOfWeek.MONDAY, LocalTime.of(1, 30),
                LocalTime.of(3, 30), null, null, expectedTeacher);

        when(lessonMapper.lessonDTOToLesson(expectedLessonDTO)).thenReturn(newLesson);

        when(repository.existsAllByTeacherAndStartTimeBetweenAndDayOfWeek(newLesson.getTeacher(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> lessonService.registerLesson(expectedLessonDTO));

        verify(lessonMapper, times(1)).lessonDTOToLesson(expectedLessonDTO);

        verify(repository, times(1)).existsAllByTeacherAndStartTimeBetweenAndDayOfWeek(newLesson.getTeacher(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek());
    }
}