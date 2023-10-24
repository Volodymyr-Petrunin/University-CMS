package com.university.universitycms;

import com.university.universitycms.domain.Course;
import com.university.universitycms.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    private final CourseRepository repository;

    @Autowired
    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> getAllCourse(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Course> getCourseById(long courseId){
        return repository.findById(courseId);
    }

    public void createCourse(Course course){
        repository.save(course);
    }

    public void createSeveralCourses(List<Course> courses){
        repository.saveAll(courses);
    }

    public void updateCourse(Course course){
        repository.save(course);
    }

    public void deleteCourse(Course course){
        repository.delete(course);
    }

}
