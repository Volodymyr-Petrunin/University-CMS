package com.university.universitycms.repository;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:scripts/students_service.sql")
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private final Group expectedGroup = new Group(1L, "ABC", Collections.emptySet());

    private final List<Student> expectedStudents = List.of(
            new Student(null, Role.STUDENT, "Volodymyr", "Petrunin", null, expectedGroup, null),
            new Student(null, Role.ADMIN, "Stas", "Solyanik", null, null, null),
            new Student(null, Role.STUDENT, "Vasa", "Pupkin", null, expectedGroup, null)
    );

    private List<Student> actual;
    private List<Student> expected;

    @Test
    void testCreateStudents_ShouldInsertStudentsInDB_AndReturnCorrectList(){
        studentRepository.saveAll(expectedStudents);

        actual = studentRepository.findAll();

        expected = new ArrayList<>(expectedStudents);

        assertEquals(expected, actual);
    }

}