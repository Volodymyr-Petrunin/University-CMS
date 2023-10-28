package com.university.universitycms.generations.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.generations.GenerationData;
import com.university.universitycms.services.CourseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class TeacherGenerationData implements GenerationData<Teacher> {
    private final Random random = new Random();

    @Value("${teachersInitialQuantityGenerations}") private int quantity;
    private final List<String> firstNames;
    private final List<String> secondNames;
    private final CourseService courseService;

    public TeacherGenerationData(@Qualifier("teacherFirstNames") List<String> firstNames,
                                 @Qualifier("teacherSecondNames") List<String> secondNames, CourseService courseService) {
        this.firstNames = firstNames;
        this.secondNames = secondNames;
        this.courseService = courseService;
    }

    @Override
    public List<Teacher> generateData() {
        List<Teacher> result = new ArrayList<>();
        List<Course> courses = courseService.getAllCourse();

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
