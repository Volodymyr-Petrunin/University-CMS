package com.university.universitycms.services;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:db/migration/V1__Model_Init.sql")
@Sql(scripts = "classpath:scripts/department_service.sql")
class DepartmentServiceTest {
    @Autowired
    private DepartmentService departmentService;

    private final Course expectedCourses = new Course(1L, "Match");
    private final Department expectedDepartment = new Department(null, "IT", List.of(expectedCourses));

    @Test
    void testCreateDepartment_ShouldCreateDepartmentInDB_AndReturnCorrectList() {
        departmentService.createDepartment(expectedDepartment);

        Optional<Department> departmentOptional = departmentService.getDepartmentById(1);

        departmentOptional.ifPresent(department -> assertEquals(expectedDepartment, department));
    }

}