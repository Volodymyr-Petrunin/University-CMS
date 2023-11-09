package com.university.universitycms.generations.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.generations.GenerationData;
import com.university.universitycms.readers.ResourcesFileReader;
import com.university.universitycms.services.CourseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class TeacherGenerationData implements GenerationData<Teacher> {
    private final Random random;
    private final int quantity;
    private final ResourcesFileReader resourcesFileReader;
    private final String firstNameFile;
    private final String secondNameFile;
    private final CourseService courseService;

    public TeacherGenerationData(Random random, ResourcesFileReader resourcesFileReader,
                                 @Value("${quantity.max.teachers}") int quantity,
                                 @Value("${generation.file.teachersName}") String firstNameFile,
                                 @Value("${generation.file.teachersSurname}") String secondNameFile,
                                 CourseService courseService) {
        this.random = random;
        this.quantity = quantity;
        this.resourcesFileReader = resourcesFileReader;
        this.firstNameFile = firstNameFile;
        this.secondNameFile = secondNameFile;
        this.courseService = courseService;
    }

    @Override
    public List<Teacher> generateData() {
        List<String> firstNames = resourcesFileReader.read(firstNameFile);
        List<String> secondNames = resourcesFileReader.read(secondNameFile);
        List<Course> courses = courseService.getAllCourse();

        List<Teacher> result = new ArrayList<>();

        for (int index = 0; index < quantity; index++) {
            String name = getRandomElement(firstNames);
            String surname = getRandomElement(secondNames);
            Course course = getRandomElement(courses);

            result.add(new Teacher(null, Role.TEACHER, name, surname, null, Set.of(course)));
        }

        return result;
    }

    private <T> T getRandomElement(List<T> list){
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
