package com.university.universitycms.generation.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.generation.GenerationData;
import com.university.universitycms.reader.ResourcesFileReader;
import com.university.universitycms.service.CourseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TeacherGenerationData implements GenerationData<Teacher> {
    private final GenerationRandomizer generationRandomizer;
    private final int quantity;
    private final ResourcesFileReader resourcesFileReader;
    private final String firstNameFile;
    private final String secondNameFile;
    private final CourseService courseService;
    private final GenerationPassword generationPassword;

    public TeacherGenerationData(GenerationRandomizer generationRandomizer, ResourcesFileReader resourcesFileReader,
                                 CourseService courseService, GenerationPassword generationPassword,
                                 @Value("${quantity.max.teachers}") int quantity,
                                 @Value("${generation.file.teachersName}") String firstNameFile,
                                 @Value("${generation.file.teachersSurname}") String secondNameFile) {
        this.generationRandomizer = generationRandomizer;
        this.quantity = quantity;
        this.resourcesFileReader = resourcesFileReader;
        this.firstNameFile = firstNameFile;
        this.secondNameFile = secondNameFile;
        this.courseService = courseService;
        this.generationPassword = generationPassword;
    }

    @Override
    public List<Teacher> generateData() {
        List<String> firstNames = resourcesFileReader.read(firstNameFile);
        List<String> secondNames = resourcesFileReader.read(secondNameFile);
        List<Course> courses = courseService.getAllCourses();

        List<Teacher> result = new ArrayList<>();

        for (int index = 0; index < quantity; index++) {
            String name = generationRandomizer.getRandomElementFromList(firstNames);
            String surname = generationRandomizer.getRandomElementFromList(secondNames);
            Course course = generationRandomizer.getRandomElementFromList(courses);
            String password = generationPassword.generatePassword();

            result.add(new Teacher(null, Role.TEACHER, name, surname, password, Set.of(course)));
        }

        return result;
    }
}
