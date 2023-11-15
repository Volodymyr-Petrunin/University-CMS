package com.university.universitycms.services;

import com.university.universitycms.domain.Department;
import com.university.universitycms.repositories.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(value = "classpath:scripts/department_service.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;
    private final Department expectedDepartment = new Department(null, "IT", Set.of());

    @Test
    void testCreateDepartment_ShouldCreateDepartmentInDB_AndReturnCorrectList() {
        departmentRepository.save(expectedDepartment);

        Optional<Department> departmentOptional = departmentRepository.findById(1L);

        assertTrue(departmentOptional.isPresent());
        assertEquals(expectedDepartment, departmentOptional.get());
    }

}