package com.university.universitycms.services;

import com.university.universitycms.domain.*;
import com.university.universitycms.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository repository;

    @Autowired
    public LessonService(LessonRepository repository) {
        this.repository = repository;
    }

    public List<Lesson> getAllLessons(){
        return repository.findAll();
    }

    public void createLesson(Lesson lesson){
        repository.save(lesson);
    }

    public void createSeveralLessons(List<Lesson> lessons){
        repository.saveAll(lessons);
    }

    public void updateLesson(Lesson lesson){
        repository.save(lesson);
    }

    public void deleteLesson(Lesson lesson){
        repository.delete(lesson);
    }

    public List<Lesson> findAllLessonsByTeacher(Teacher teacher){
        List<Course> courses = teacher.getCourses();

        if (courses.isEmpty()) {
            throw new IllegalArgumentException("Teacher don't have lessons");
        }

        return repository.findLessonsByTeacherCourses(courses);
    }

    public List<Lesson> findAllLessonsByStudent(Student student) {
        Group group = student.getGroup();

        if (group == null){
            throw new IllegalArgumentException("Student don't have group");
        }

        return repository.findAllLessonByStudentGroup(group);
    }
}
