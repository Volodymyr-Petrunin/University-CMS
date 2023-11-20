package com.university.universitycms.generation.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Department;
import com.university.universitycms.generation.GenerationData;
import com.university.universitycms.reader.ResourcesFileReader;
import com.university.universitycms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DepartmentGenerationData implements GenerationData<Department> {
    private final Random random;
    private final ResourcesFileReader resourcesFileReader;
    private final CourseService courseService;
    private final int courseInDepartment;
    private final String fileName;

    @Autowired
    public DepartmentGenerationData(Random random, ResourcesFileReader resourcesFileReader,
                                    CourseService courseService,
                                    @Value("${quantity.max.courseInDepartment}") int courseInDepartment,
                                    @Value("${generation.file.departments}") String fileName) {
        this.random = random;
        this.resourcesFileReader = resourcesFileReader;
        this.courseService = courseService;
        this.courseInDepartment = courseInDepartment;
        this.fileName = fileName;
    }

    @Override
    public List<Department> generateData() {
        List<Course> courses = courseService.getAllCourse();

        if (courses.isEmpty()){
            throw new IllegalArgumentException("Courses list is empty");
        }

        return resourcesFileReader.read(fileName).stream()
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
