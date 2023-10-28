package com.university.universitycms.generations.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.generations.GenerationData;
import com.university.universitycms.services.CourseService;
import com.university.universitycms.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class LessonGenerationData implements GenerationData<Lesson> {
    private final Random random = new Random();
    private final GroupService groupService;
    private final CourseService courseService;
    private final List<String> audiences;
    private DayOfWeek currentDayOfWeek = DayOfWeek.MONDAY;

    @Value("${lessonQuantityInWeek}") int quantity;

    @Autowired
    public LessonGenerationData(GroupService groupService, CourseService courseService,
                                @Qualifier("audiencesList") List<String> audiences) {
        this.groupService = groupService;
        this.courseService = courseService;
        this.audiences = audiences;
    }

    @Override
    public List<Lesson> generateData() {
        List<Group> groups = groupService.getAllGroups();
        List<Course> courses = courseService.getAllCourse();

        List<Lesson> result = new ArrayList<>();

        for (int index = 0; index < quantity; index++) {
            Group group = getRandomElement(groups);
            Course course = getRandomElement(courses);
            String audience = getRandomElement(audiences);
            LocalTime startTime = getStartTime();


            result.add(new Lesson(null, createLessonName(course, group), audience, getDayOfWeek(index), startTime,
                    startTime.plusHours(2), course, group));
        }

        return result;
    }

    private String createLessonName(Course course, Group group){
        return course.getName() + " group " + group.getName();
    }

    private DayOfWeek getDayOfWeek(int index){
        if (index % 5 == 0) {
            currentDayOfWeek = currentDayOfWeek.plus(1);
        }

        if (currentDayOfWeek == DayOfWeek.SATURDAY) {
            currentDayOfWeek = DayOfWeek.MONDAY;
        }

        return currentDayOfWeek;
    }

    private LocalTime getStartTime(){
        return LocalTime.of(random.nextInt(8, 15), 0);
    }

    private <T> T getRandomElement(List<T> list){
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
