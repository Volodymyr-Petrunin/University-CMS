package com.university.universitycms.services;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:db/migration/V1__Model_Init.sql")
@Sql(scripts = "classpath:scripts/teacher_service.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TeacherServiceTest {
    @Autowired
    private TeacherService teacherService;

    private final List<Teacher> expectedTeacher = List.of(
            new Teacher(null, null, "Volodymyr", "Petrunin", null),
            new Teacher(null, null, "Stas", "Solyanik", null),
            new Teacher(null, null, "Prepod", "Batikovich", null)
    );
    private final Course expectedCourse = new Course(1L,"IT");
    private List<Teacher> actual;

    @BeforeEach
    void setUp() {
        for (Teacher teacher : expectedTeacher){
            teacher.addCourses(expectedCourse);
        }

        teacherService.createSeveralTeachers(expectedTeacher);
    }

    @Test
    void testFindAllTeacherRelativeToCourse_ShouldReturnCorrectList(){
        actual = teacherService.findAllTeacherRelativeToCourse(expectedCourse);

        assertEquals(expectedTeacher, actual);
    }

}