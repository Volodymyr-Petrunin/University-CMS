package com.university.universitycms.service;

import com.university.universitycms.domain.Student;
import com.university.universitycms.email.EmailSender;
import com.university.universitycms.generation.impl.PasswordGeneration;
import com.university.universitycms.generation.impl.StudentGenerationData;
import com.university.universitycms.repository.StudentRepository;
import com.university.universitycms.filldata.DataFiller;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService implements DataFiller {
    private final StudentRepository repository;
    private final StudentGenerationData studentGenerationData;
    private final EmailSender emailSender;
    private final PasswordGeneration passwordGeneration;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(StudentRepository repository, StudentGenerationData studentGenerationData, EmailSender emailSender,
                          PasswordGeneration passwordGeneration, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.studentGenerationData = studentGenerationData;
        this.emailSender = emailSender;
        this.passwordGeneration = passwordGeneration;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Student> getAllStudents(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Student> getStudentById(long studentId){
        return repository.findById(studentId);
    }

    public void createStudent(Student student){
        char[] password = passwordGeneration.generatePassword();
        student.setPassword(passwordEncoder.encode(CharBuffer.wrap(password)));
        repository.save(student);

        emailSender.sendRegistrationConfirmation(student.getName(), student.getEmail(), password);
        Arrays.fill(password, '\0');
    }

    public void createSeveralStudents(List<Student> students){
        for (Student student : students){
            char[] password = passwordGeneration.generatePassword();
            student.setPassword(passwordEncoder.encode(CharBuffer.wrap(password)));

            emailSender.sendRegistrationConfirmation(student.getName(), student.getEmail(), password);
            Arrays.fill(password, '\0');
        }

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
