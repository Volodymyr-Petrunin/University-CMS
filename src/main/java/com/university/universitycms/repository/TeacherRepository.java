package com.university.universitycms.repository;

import com.university.universitycms.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Set<Teacher> findAllByIdIn(Set<Long> id);
}
