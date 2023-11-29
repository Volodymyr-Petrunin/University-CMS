package com.university.universitycms.service;

import com.university.universitycms.domain.Teacher;
import com.university.universitycms.email.EmailSender;
import com.university.universitycms.generation.impl.PasswordGeneration;
import com.university.universitycms.generation.impl.TeacherGenerationData;
import com.university.universitycms.repository.TeacherRepository;
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
public class TeacherService implements DataFiller {
    private final TeacherRepository repository;
    private final TeacherGenerationData teacherGenerationData;
    private final EmailSender emailSender;
    private final PasswordGeneration passwordGeneration;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TeacherService(TeacherRepository repository, TeacherGenerationData teacherGenerationData,
                          EmailSender emailSender, PasswordGeneration passwordGeneration, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.teacherGenerationData = teacherGenerationData;
        this.emailSender = emailSender;
        this.passwordGeneration = passwordGeneration;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Teacher> getAllTeachers(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Teacher> getTeacherById(long teacherId){
        return repository.findById(teacherId);
    }

    public void createTeacher(Teacher teacher){
        emailSender.sendRegistrationConfirmation(teacher.getName(), teacher.getEmail(), teacher.getPassword().toCharArray());

        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        repository.save(teacher);
    }

    public void createSeveralTeachers(List<Teacher> teachers){
        for (Teacher teacher : teachers){
            char[] password = passwordGeneration.generatePassword();
            teacher.setPassword(passwordEncoder.encode(CharBuffer.wrap(password)));

            emailSender.sendRegistrationConfirmation(teacher.getName(), teacher.getEmail(), password);
            Arrays.fill(password, '\0');
        }
        repository.saveAll(teachers);
    }

    public void updateTeacher(Teacher teacher){
        repository.save(teacher);
    }

    public void deleteTeacher(Teacher teacher){
        repository.delete(teacher);
    }

    @Override
    public void fillData() {
        createSeveralTeachers(teacherGenerationData.generateData());
    }
}
