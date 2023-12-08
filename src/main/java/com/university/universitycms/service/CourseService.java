package com.university.universitycms.service;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Department;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.generation.impl.CourseGenerationData;
import com.university.universitycms.repository.CourseRepository;
import com.university.universitycms.filldata.DataFiller;
import com.university.universitycms.repository.DepartmentRepository;
import com.university.universitycms.repository.LessonRepository;
import com.university.universitycms.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CourseService implements DataFiller {
    private final CourseRepository repository;
    private final CourseGenerationData courseGenerationData;
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public CourseService(CourseRepository repository, CourseGenerationData courseGenerationData,
                         TeacherRepository teacherRepository, LessonRepository lessonRepository,
                         DepartmentRepository departmentRepository) {
        this.repository = repository;
        this.courseGenerationData = courseGenerationData;
        this.teacherRepository = teacherRepository;
        this.lessonRepository = lessonRepository;
        this.departmentRepository = departmentRepository;
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

    public void updateCourse(Course course){
        repository.save(course);
    }

    public void deleteCourse(Course course){
        repository.delete(course);
    }

    public void deleteCourseById(long id){
        Course course = this.getCourseById(id);

        List<Teacher> teachersRelativeToCourse = teacherRepository.findAllByCoursesContaining(course);
        List<Department> departments = departmentRepository.findAllByCoursesContaining(course);

        teachersRelativeToCourse.forEach(teacher -> teacher.getCourses().remove(course));
        departments.forEach(department -> department.getCourses().remove(course));

        departmentRepository.saveAll(departments);
        lessonRepository.deleteAllByCourse(course);
        teacherRepository.saveAll(teachersRelativeToCourse);

        repository.deleteById(id);
    }

    @Override
    public void fillData() {
        createSeveralCourses(courseGenerationData.generateData());
    }
}
