package com.university.universitycms.services;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:db/migration/V1__Model_Init.sql")
@Sql(scripts = "classpath:scripts/department_service.sql")
class DepartmentServiceTest {
    @Autowired
    private DepartmentService departmentService;

    private final Department expectedDepartment = new Department(null, "IT");
    private final Course expectedCourses = new Course(1L, "Match");

    private Department actual;

    @BeforeEach
    void setUp() {
        expectedDepartment.addCourse(expectedCourses);

        departmentService.createDepartment(expectedDepartment);
    }

    @Test
    void testGetDepartmentByCourse_ShouldReturn(){
        actual = departmentService.getDepartmentByCourse(expectedCourses);

        assertEquals(expectedDepartment, actual);
    }
}