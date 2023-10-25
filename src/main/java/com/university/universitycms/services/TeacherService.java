package com.university.universitycms.services;

import com.university.universitycms.domain.Teacher;
import com.university.universitycms.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeacherService {
    private final TeacherRepository repository;

    @Autowired
    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public List<Teacher> getAllTeachers(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Teacher> getTeacherById(long teacherId){
        return repository.findById(teacherId);
    }

    public void createTeacher(Teacher teacher){
        repository.save(teacher);
    }

    public void createSeveralTeachers(List<Teacher> teachers){
        repository.saveAll(teachers);
    }

    public void updateTeacher(Teacher teacher){
        repository.save(teacher);
    }

    public void deleteTeacher(Teacher teacher){
        repository.delete(teacher);
    }
}
