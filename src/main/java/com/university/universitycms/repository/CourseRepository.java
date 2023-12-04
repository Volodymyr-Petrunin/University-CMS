package com.university.universitycms.repository;

import com.university.universitycms.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Set<Course> findAllByIdIn(List<Long> id);
}
