package com.university.universitycms.repository;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:scripts/teacher_service.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;

    private final Course expectedCourse = new Course(1L,"IT", Collections.emptySet());
    private final List<Teacher> expectedTeacher = List.of(
            new Teacher(null, Role.TEACHER, "Volodymyr", "Petrunin", null, Set.of(expectedCourse), null),
            new Teacher(null, Role.ADMIN, "Stas", "Solyanik", null, Set.of(), null),
            new Teacher(null, Role.TEACHER, "Prepod", "Batikovich", null, Set.of(expectedCourse), null)
    );
    private List<Teacher> actual;
    private List<Teacher> expected;

    @Test
    void testCreateTeachers_ShouldInsertTeachersInDB_AndReturnCorrectList() {
        teacherRepository.saveAll(expectedTeacher);

        actual = teacherRepository.findAll();

        expected = new ArrayList<>(expectedTeacher);

        assertEquals(expected, actual);
    }

}