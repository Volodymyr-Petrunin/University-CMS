package com.university.universitycms.repository;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByCoursesContaining(Course course);
}
