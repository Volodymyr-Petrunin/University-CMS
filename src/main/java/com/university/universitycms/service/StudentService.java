package com.university.universitycms.service;

import com.university.universitycms.domain.Student;
import com.university.universitycms.generation.impl.StudentGenerationData;
import com.university.universitycms.repository.StudentRepository;
import com.university.universitycms.filldata.DataFiller;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService implements DataFiller {

    private final StudentRepository repository;
    private final StudentGenerationData studentGenerationData;

    @Autowired
    public StudentService(StudentRepository repository, StudentGenerationData studentGenerationData) {
        this.repository = repository;
        this.studentGenerationData = studentGenerationData;
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

    @Override
    public void fillData() {
        createSeveralStudents(studentGenerationData.generateData());
    }
}
