package com.university.universitycms.services;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:db/migration/V1__Model_Init.sql")
@Sql(scripts = "classpath:scripts/students_service.sql")
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    private final Group expectedGroup = new Group(1L, "ABC");

    private final List<Student> expectedStudents = List.of(
            new Student(null, Role.STUDENT, "Volodymyr", "Petrunin", null, expectedGroup),
            new Student(null, Role.ADMIN, "Stas", "Solyanik", null, null),
            new Student(null, Role.STUDENT, "Vasa", "Pupkin", null, expectedGroup)
    );

    private List<Student> actual;
    private List<Student> expected;

    @Test
    void testCreateStudents_ShouldInsertStudentsInDB_AndReturnCorrectList(){
        studentService.createSeveralStudents(expectedStudents);

        actual = studentService.getAllStudents();

        expected = new ArrayList<>(expectedStudents);

        assertEquals(expected, actual);
    }

}