package com.university.universitycms.services;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TeacherService {
    private final TeacherRepository repository;

    @Autowired
    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public List<Teacher> getAllTeachers(){
        return repository.findAll();
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

    public List<Teacher> findAllTeacherRelativeToCourse(Course course){
        return repository.findAllByCoursesContaining(course);
    }
}
