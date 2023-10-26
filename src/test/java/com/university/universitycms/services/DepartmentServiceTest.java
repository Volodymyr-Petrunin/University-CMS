package com.university.universitycms.services;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:db/migration/V1__Model_Init.sql")
@Sql(scripts = "classpath:scripts/department_service.sql")
class DepartmentServiceTest {
    @Autowired
    private DepartmentService departmentService;
    private final Department expectedDepartment = new Department(null, "IT", Set.of());

    @Test
    void testCreateDepartment_ShouldCreateDepartmentInDB_AndReturnCorrectList() {
        departmentService.createDepartment(expectedDepartment);

        Optional<Department> departmentOptional = departmentService.getDepartmentById(1);

        assertTrue(departmentOptional.isPresent());
        assertEquals(expectedDepartment, departmentOptional.get());
    }

}