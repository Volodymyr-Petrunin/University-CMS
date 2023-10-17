package com.university.universitycms.repositories;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t WHERE t.courses = :course")
    List<Teacher> findAllTeacherRelativeToCourse(@Param("course") Course course);

    List<Teacher> findAllByCoursesContaining(Course course);
}
