package com.university.universitycms.services;

import com.university.universitycms.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:db/migration/V1__Model_Init.sql" )
@Sql(scripts = "classpath:scripts/lesson_service.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LessonServiceTest {
    @Autowired
    private LessonService lessonService;
    private final Course expectedCourse = new Course(1L, "IT");
    private final Group expectedGroup = new Group(1L, "A12");

    private final List<Lesson> expectedLessons = List.of(
            new Lesson(null, "ENG group 1", "A105", null, null, null, expectedCourse, expectedGroup),
            new Lesson(null, "ENG group 2", "A106", null, null, null, expectedCourse, null),
            new Lesson(null, "ENG group 3", "A107", null, null, null, null, expectedGroup)
    );
    private List<Lesson> actual;
    private List<Lesson> expected;

    @BeforeEach
    void setUp() {
        for (Lesson lesson : expectedLessons) {
            lessonService.createLesson(lesson);
        }

    }

    @Test
    void testFindAllLessonsByTeacher_ShouldReturnCorrectList(){
        Teacher teacher = new Teacher(1L, Role.TEACHER, "Prepod", "Batikovich", null);
        teacher.addCourses(expectedCourse);

        actual = lessonService.findAllLessonsByTeacher(teacher);

        expected = new ArrayList<>(expectedLessons);
        expected.remove(2);

        assertEquals(expected, actual);
    }

    @Test
    void testFindAllLessonsByStudent_ShouldReturnCorrectList(){
        Student student = new Student(1L, Role.STUDENT, "Volodymyr", "Petrunin", null);
        student.setGroup(expectedGroup);

        actual = lessonService.findAllLessonsByStudent(student);

        expected = new ArrayList<>(expectedLessons);
        expected.remove(1);

        assertEquals(expected, actual);
    }
}