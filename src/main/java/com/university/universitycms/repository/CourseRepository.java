package com.university.universitycms.repository;

import com.university.universitycms.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByIdIn(List<Long> id);
}
