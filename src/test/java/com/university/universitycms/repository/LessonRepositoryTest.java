package com.university.universitycms.repository;

import com.university.universitycms.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(value = "classpath:scripts/lesson_service.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LessonRepositoryTest {
    @Autowired
    private LessonRepository lessonRepository;
    private final Course expectedCourse = new Course(1L, "IT", Collections.emptySet());
    private final Group expectedGroup = new Group(1L, "A12", Collections.emptySet());
    private final Teacher expectedTeacher = new Teacher(1L, Role.TEACHER, "Oleg", "Tot", null,
            Collections.singleton(expectedCourse), null);
    private List<Lesson> actual;
    private List<Lesson> expected;

    private final List<Lesson> expectedLessons = List.of(
            new Lesson(null, "ENG group 1", "A105", DayOfWeek.FRIDAY, LocalTime.of(11, 0),
                    LocalTime.of(12, 0), expectedCourse, expectedGroup, expectedTeacher),
            new Lesson(null, "ENG group 2", "A106", DayOfWeek.THURSDAY, LocalTime.of(9, 30),
                    LocalTime.of(11, 30), expectedCourse, null, expectedTeacher),
            new Lesson(null, "ENG group 3", "A107", DayOfWeek.SUNDAY, LocalTime.of(15, 0),
                    LocalTime.of(17, 0), null, expectedGroup, null)
    );

    @BeforeEach
    void setUp() {
        lessonRepository.saveAll(expectedLessons);
    }

    @Test
    void testSaveLesson_ShouldCreateNewLessonInDB_AndReturnCorrectList(){
        Lesson newLesson = new Lesson(null, "NewLesson", "B12", DayOfWeek.MONDAY, null, null, null, null, expectedTeacher);

        lessonRepository.save(newLesson);

        actual = lessonRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        expected = new ArrayList<>(expectedLessons);
        expected.add(newLesson);

        assertEquals(expected, actual);
    }

    @Test
    void testSaveLesson_ShouldUpdateLessonInDB_AndReturnCorrectList(){
        Lesson lessonForUpdate = expectedLessons.get(0);

        //set data
        lessonForUpdate.setAudience("ABC");
        lessonForUpdate.setName("UA Language");

        lessonRepository.save(lessonForUpdate);

        actual = lessonRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        expected = new ArrayList<>(expectedLessons);
        expected.set(0, lessonForUpdate);

        assertEquals(expected, actual);
    }

    @Test
    void testFindLessonByDayOfWeekAndGroupOrderByStartTimeAsc_ShouldFindCorrectLessons_AndReturnCorrectList(){
        actual = lessonRepository.findLessonByDayOfWeekAndGroupOrderByStartTimeAsc(DayOfWeek.FRIDAY, expectedGroup);

        Lesson expected = expectedLessons.get(0);

        assertEquals(List.of(expected), actual);
    }

    @Test
    void testFindLessonsByDayOfWeekAndCourseInOrderByStartTimeAsc_ShouldFindCorrectLessons_AndReturnCorrectList(){
        actual = lessonRepository
                .findLessonsByDayOfWeekAndTeacherOrderByStartTimeAsc(DayOfWeek.FRIDAY, expectedTeacher);

        Lesson expected = expectedLessons.get(0);

        assertEquals(List.of(expected), actual);
    }

    @Test
    void testFindLessonsByGroupOrderByDayOfWeekAscStartTimeAsc_ShouldFindCorrectLessons_AndReturnCorrectList(){
        actual = lessonRepository.findLessonsByGroupOrderByDayOfWeekAscStartTimeAsc(expectedGroup);

        expected = new ArrayList<>(expectedLessons);
        expected.remove(1); // remove second lesson because it not has a group

        assertEquals(expected, actual);
    }

    @Test
    void testFindLessonsByCourseInOrderByDayOfWeekAscStartTimeAsc_ShouldFindCorrectLessons_AndReturnCorrectList(){
        actual = lessonRepository.findLessonsByTeacherOrderByDayOfWeekAscStartTimeAsc(expectedTeacher);

        expected = new ArrayList<>(expectedLessons);
        expected.remove(2); // remove second lesson because it not has a course

        assertEquals(expected, actual);
    }
}