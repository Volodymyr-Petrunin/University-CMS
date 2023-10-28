package com.university.universitycms.generations.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Department;
import com.university.universitycms.generations.GenerationData;
import com.university.universitycms.readers.ResourcesFileReader;
import com.university.universitycms.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DepartmentGenerationData implements GenerationData<Department> {
    private final Random random = new Random();
    private final ResourcesFileReader resourcesFileReader;
    private final CourseService courseService;

    @Value("${courseInDepartment}") private int courseInDepartment;

    @Autowired
    public DepartmentGenerationData(@Qualifier("readerDepartmentFile") ResourcesFileReader resourcesFileReader, CourseService courseService) {
        this.resourcesFileReader = resourcesFileReader;
        this.courseService = courseService;
    }

    @Override
    public List<Department> generateData() {
        List<Course> courses = courseService.getAllCourse();

        if (courses.isEmpty()){
            throw new IllegalArgumentException("Courses list is empty");
        }

        return resourcesFileReader.read().stream()
                .map(line -> new Department(null, line, getRandomCoursesFromList(courses)))
                .toList();
    }

    private Set<Course> getRandomCoursesFromList(List<Course> list){
         List<Course> courses = new ArrayList<>(list);
         Set<Course> courseSet = new HashSet<>();

        for (int index = 0; index < courseInDepartment && index < courses.size(); index++) {
            int randomIndex = random.nextInt(courses.size());
            courseSet.add(courses.remove(randomIndex));
        }

        return courseSet;
    }
}
