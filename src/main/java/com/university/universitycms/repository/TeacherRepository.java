package com.university.universitycms.repository;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByCoursesContaining(Course course);
    List<Teacher> findAllByIdIn(List<Long> id);
}
