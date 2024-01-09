package com.university.universitycms.service;

import com.university.universitycms.domain.*;
import com.university.universitycms.domain.dto.LessonDTO;
import com.university.universitycms.domain.mapper.LessonMapper;
import com.university.universitycms.repository.LessonRepository;
import com.university.universitycms.service.exception.AudienceNotFreeException;
import com.university.universitycms.service.exception.GroupNotFreeException;
import com.university.universitycms.service.exception.TeacherNotFreeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

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
    private final Lesson expectedLesson = new Lesson(1L, "ENG group 4", "A108", DayOfWeek.MONDAY, LocalTime.of(1, 30),
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
        when(repository.findAllByAudienceAndStartTimeBetweenAndDayOfWeek(expectedLesson.getAudience(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek())).thenReturn(Collections.emptyList());
        when(repository.findAllByGroupAndStartTimeBetweenAndDayOfWeek(expectedLesson.getGroup(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek())).thenReturn(Collections.emptyList());
        when(repository.findAllByTeacherAndStartTimeBetweenAndDayOfWeek(expectedLesson.getTeacher(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek())).thenReturn(Collections.emptyList());

        lessonService.registerLesson(expectedLessonDTO);

        verify(lessonMapper).lessonDTOToLesson(expectedLessonDTO);
        verify(repository).findAllByAudienceAndStartTimeBetweenAndDayOfWeek(expectedLesson.getAudience(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek());
        verify(repository).findAllByGroupAndStartTimeBetweenAndDayOfWeek(expectedLesson.getGroup(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek());
        verify(repository).findAllByTeacherAndStartTimeBetweenAndDayOfWeek(expectedLesson.getTeacher(), expectedLesson.getStartTime(),
                expectedLesson.getEndTime(), expectedLesson.getDayOfWeek());
        verify(repository).save(expectedLesson);
        verify(repository, times(1)).save(expectedLesson);
    }

    @Test
    void testRegisterLesson_AudienceNotFree_ExceptionThrown() {
        Lesson newLesson = new Lesson(2L, "PE group 1", expectedLesson.getAudience(), DayOfWeek.MONDAY, LocalTime.of(1, 30),
                LocalTime.of(3, 30), null, null, null);
        when(lessonMapper.lessonDTOToLesson(expectedLessonDTO)).thenReturn(newLesson);
        when(repository.findAllByAudienceAndStartTimeBetweenAndDayOfWeek(newLesson.getAudience(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek())).thenReturn(List.of(expectedLesson));

        assertThrows(AudienceNotFreeException.class, () -> lessonService.registerLesson(expectedLessonDTO));

        verify(lessonMapper, times(1)).lessonDTOToLesson(expectedLessonDTO);
        verify(repository, times(1)).findAllByAudienceAndStartTimeBetweenAndDayOfWeek(newLesson.getAudience(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek());
    }

    @Test
    void testRegisterLesson_GroupNotFree_ExceptionThrow(){
        Lesson newLesson = new Lesson(2L, "PE group 1", "SLS", DayOfWeek.MONDAY, LocalTime.of(1, 30),
                LocalTime.of(3, 30), null, expectedGroup, null);
        when(lessonMapper.lessonDTOToLesson(expectedLessonDTO)).thenReturn(newLesson);
        when(repository.findAllByGroupAndStartTimeBetweenAndDayOfWeek(newLesson.getGroup(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek())).thenReturn(List.of(expectedLesson));

        assertThrows(GroupNotFreeException.class, () -> lessonService.registerLesson(expectedLessonDTO));

        verify(lessonMapper, times(1)).lessonDTOToLesson(expectedLessonDTO);
        verify(repository, times(1)).findAllByGroupAndStartTimeBetweenAndDayOfWeek(newLesson.getGroup(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek());
    }

    @Test
    void testRegisterLesson_TeacherNotFree_ExceptionThrow(){
        Lesson newLesson = new Lesson(2L, "PE group 1", "AMG", DayOfWeek.MONDAY, LocalTime.of(1, 30),
                LocalTime.of(3, 30), null, null, expectedTeacher);
        when(lessonMapper.lessonDTOToLesson(expectedLessonDTO)).thenReturn(newLesson);
        when(repository.findAllByTeacherAndStartTimeBetweenAndDayOfWeek(newLesson.getTeacher(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek())).thenReturn(List.of(expectedLesson));

        assertThrows(TeacherNotFreeException.class, () -> lessonService.registerLesson(expectedLessonDTO));

        verify(lessonMapper, times(1)).lessonDTOToLesson(expectedLessonDTO);
        verify(repository, times(1)).findAllByTeacherAndStartTimeBetweenAndDayOfWeek(newLesson.getTeacher(), newLesson.getStartTime(),
                newLesson.getEndTime(), newLesson.getDayOfWeek());
    }
}