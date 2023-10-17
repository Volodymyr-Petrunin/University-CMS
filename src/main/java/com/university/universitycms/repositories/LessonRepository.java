package com.university.universitycms.repositories;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("SELECT l FROM Lesson l WHERE l.course IN :courses")
    List<Lesson> findLessonsByTeacherCourses(@Param("courses")List<Course> courses);

    @Query("SELECT l FROM Lesson l WHERE l.group = :groupParam")
    List<Lesson> findAllLessonByStudentGroup(@Param("groupParam") Group group);
}
