package com.university.universitycms.services;

import com.university.universitycms.domain.*;
import com.university.universitycms.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    private final LessonRepository repository;

    @Autowired
    public LessonService(LessonRepository repository) {
        this.repository = repository;
    }

    public List<Lesson> getAllLessons(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Lesson> getLessonById(long lessonId){
        return repository.findById(lessonId);
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
}
