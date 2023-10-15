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
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    @Query("SELECT l FROM Lesson l WHERE l.course IN :courses")
    List<Lesson> findLessonsByTeacherCourses(@Param("courses")List<Course> courses);
}
