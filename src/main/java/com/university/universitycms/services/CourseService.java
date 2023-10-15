package com.university.universitycms.services;

import com.university.universitycms.domains.Course;
import com.university.universitycms.domains.Teacher;
import com.university.universitycms.repositories.CourseRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CourseService {
    private final CourseRepo repository;

    @Autowired
    public CourseService(CourseRepo repository) {
        this.repository = repository;
    }

    public List<Course> getAllCourse(){
        return repository.findAll();
    }

    public void createCourse(Course course){
        repository.save(course);
    }

    public void createSerialCourses(List<Course> courses){
        repository.saveAll(courses);
    }

    public void updateCourse(Course course){
        repository.save(course);
    }

    public void deleteCourse(Course course){
        repository.delete(course);
    }

    public List<Teacher> findAllTeacherRelativeToCourse(Course course){
        return repository.findAllTeacherRelativeToCourse(course);
    }
}
