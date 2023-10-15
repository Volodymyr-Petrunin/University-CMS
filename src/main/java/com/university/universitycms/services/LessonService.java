package com.university.universitycms.services;

import com.university.universitycms.domains.Course;
import com.university.universitycms.domains.Lesson;
import com.university.universitycms.domains.Teacher;
import com.university.universitycms.repositories.LessonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepo repository;

    @Autowired
    public LessonService(LessonRepo repository) {
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

    public List<Teacher> findAllTeachersByCourse(Lesson lesson){
        Course course = lesson.getCourse();

        if (course == null){
            throw new IllegalArgumentException("Lesson have not course");
        }

        return repository.findAllTeachersByCourse(course);
    }
}
