package com.university.universitycms.services;

import com.university.universitycms.domains.Group;
import com.university.universitycms.domains.Lesson;
import com.university.universitycms.domains.Student;
import com.university.universitycms.repositories.StudentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepo repository;

    @Autowired
    public StudentService(StudentRepo repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents(){
        return repository.findAll();
    }

    public void createStudent(Student student){
        repository.save(student);
    }

    public void createSeveralStudents(List<Student> students){
        repository.saveAll(students);
    }

    public void updateStudent(Student student){
        repository.save(student);
    }

    public void deleteStudent(Student student){
        repository.delete(student);
    }

    public List<Lesson> findAllLessonsByStudent(Student student) {
        Group group = student.getGroup();

        if (group == null){
            throw new IllegalArgumentException("Student don't have group");
        }

        return repository.findAllLessonByStudentGroup(group);
    }
}
