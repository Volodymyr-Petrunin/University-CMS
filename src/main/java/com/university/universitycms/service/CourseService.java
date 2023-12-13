package com.university.universitycms.service;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.filldata.DataFiller;
import com.university.universitycms.generation.impl.CourseGenerationData;
import com.university.universitycms.repository.CourseRepository;
import com.university.universitycms.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService implements DataFiller {
    private final CourseRepository repository;
    private final CourseGenerationData courseGenerationData;
    private final TeacherRepository teacherRepository;

    @Autowired
    public CourseService(CourseRepository repository, CourseGenerationData courseGenerationData, TeacherRepository teacherRepository) {
        this.repository = repository;
        this.courseGenerationData = courseGenerationData;
        this.teacherRepository = teacherRepository;
    }

    public List<Course> getAllCourses(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Course getCourseById(long courseId){
        return repository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Cant find course with id: " + courseId));
    }

    public void createCourse(Course course){
        repository.save(course);
    }

    public void createSeveralCourses(List<Course> courses){
        repository.saveAll(courses);
    }

    public void registerCourse(String courseName){
        this.createCourse(new Course(null, courseName));
    }

    public void updateCourse(Course course, Set<Long> teachersId){
        removeTeachersFromCourse(teachersId, course);
        addTeachersToCourse(teachersId, course);
        repository.save(course);
    }

    public void deleteCourse(Course course){
        repository.delete(course);
    }

    public void deleteCourseById(long id){
        repository.deleteById(id);
    }

    @Override
    public void fillData() {
        createSeveralCourses(courseGenerationData.generateData());
    }

    private void addTeachersToCourse(Set<Long> teachersId, Course course){
        List<Teacher> teachers = teacherRepository.findAllByIdIn(teachersId);
        teachers.forEach(teacher -> teacher.addCourses(course));

        teacherRepository.saveAll(teachers);
    }

    private void removeTeachersFromCourse(Set<Long> teachersId, Course course) {
        Set<Teacher> teachersToRemove = teacherRepository.findAllByCoursesContaining(course);

        teachersToRemove.removeIf(teacher -> teachersId.contains(teacher.getId()));

        teachersToRemove.forEach(teacher -> teacher.getCourses().remove(course));

        teacherRepository.saveAll(teachersToRemove);
    }
}
