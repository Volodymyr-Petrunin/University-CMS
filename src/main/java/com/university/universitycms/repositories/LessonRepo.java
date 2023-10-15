package com.university.universitycms.repositories;

import com.university.universitycms.domains.Course;
import com.university.universitycms.domains.Lesson;
import com.university.universitycms.domains.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepo extends JpaRepository<Lesson, Long> {
    @Query("SELECT t FROM Teacher t WHERE t.courses = :course")
    List<Teacher> findAllTeachersByCourse(@Param("course") Course course);
}
