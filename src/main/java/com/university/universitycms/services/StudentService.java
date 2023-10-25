package com.university.universitycms.services;

import com.university.universitycms.domain.Student;
import com.university.universitycms.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Student> getStudentById(long studentId){
        return repository.findById(studentId);
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

}
