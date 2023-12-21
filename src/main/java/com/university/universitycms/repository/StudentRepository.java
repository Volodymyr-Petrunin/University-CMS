package com.university.universitycms.repository;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Set<Student> findAllByGroup(Group group);
}
