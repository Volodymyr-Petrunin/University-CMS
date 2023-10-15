package com.university.universitycms.repositories;

import com.university.universitycms.domains.Group;
import com.university.universitycms.domains.Lesson;
import com.university.universitycms.domains.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    @Query("SELECT l FROM Lesson l WHERE l.group = :groupParam")
    List<Lesson> findAllLessonByStudentGroup(@Param("groupParam") Group group);
}
