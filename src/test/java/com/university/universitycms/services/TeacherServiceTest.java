package com.university.universitycms.services;

import com.university.universitycms.TeacherService;
import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:db/migration/V1__Model_Init.sql")
@Sql(scripts = "classpath:scripts/teacher_service.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TeacherServiceTest {
    @Autowired
    private TeacherService teacherService;

    private final Course expectedCourse = new Course(1L,"IT");
    private final List<Teacher> expectedTeacher = List.of(
            new Teacher(null, Role.TEACHER, "Volodymyr", "Petrunin", null, Set.of(expectedCourse)),
            new Teacher(null, Role.ADMIN, "Stas", "Solyanik", null, Set.of()),
            new Teacher(null, Role.TEACHER, "Prepod", "Batikovich", null, Set.of(expectedCourse))
    );
    private List<Teacher> actual;
    private List<Teacher> expected;

    @Test
    void testCreateTeacher_ShouldInsertTeacherInDB_AndReturnCorrectList() {
        teacherService.createSeveralTeachers(expectedTeacher);

        actual = teacherService.getAllTeachers();

        expected = new ArrayList<>(expectedTeacher);

        assertEquals(expected, actual);
    }

}