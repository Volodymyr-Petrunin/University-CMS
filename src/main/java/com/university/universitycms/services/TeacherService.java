package com.university.universitycms.services;

import com.university.universitycms.domains.Course;
import com.university.universitycms.domains.Lesson;
import com.university.universitycms.domains.Teacher;
import com.university.universitycms.repositories.TeacherRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TeacherService {
    private final TeacherRepo repository;

    @Autowired
    public TeacherService(TeacherRepo repository) {
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

    public List<Lesson> findAllLessonsByTeacher(Teacher teacher){
        List<Course> courses = teacher.getCourses();

        if (courses.isEmpty()) {
            throw new IllegalArgumentException("Teacher don't have lessons");
        }

        return repository.findLessonsByTeacherCourses(courses);
    }
}
