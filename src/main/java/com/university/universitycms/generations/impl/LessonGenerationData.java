package com.university.universitycms.generations.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.generations.GenerationData;
import com.university.universitycms.generations.randomutils.RandomUtils;
import com.university.universitycms.readers.ResourcesFileReader;
import com.university.universitycms.services.CourseService;
import com.university.universitycms.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class LessonGenerationData implements GenerationData<Lesson> {
    private final RandomUtils randomUtils;
    private final Clock clock;
    private final GroupService groupService;
    private final CourseService courseService;
    private final ResourcesFileReader resourcesFileReader;
    private final String audienceFile;
    private final int quantity;
    private DayOfWeek currentDayOfWeek;

    @Autowired
    public LessonGenerationData(RandomUtils randomUtils, Clock clock, GroupService groupService, CourseService courseService,
                                ResourcesFileReader resourcesFileReader,
                                @Value("${generation.file.audiences}") String audienceFile,
                                @Value("${quantity.max.lessonInWeek}") int quantity) {
        this.randomUtils = randomUtils;
        this.clock = clock;
        this.groupService = groupService;
        this.courseService = courseService;
        this.resourcesFileReader = resourcesFileReader;
        this.audienceFile = audienceFile;
        this.quantity = quantity;
    }

    @Override
    public List<Lesson> generateData() {
        List<String> audiences = resourcesFileReader.read(audienceFile);
        List<Group> groups = groupService.getAllGroups();
        List<Course> courses = courseService.getAllCourse();
        currentDayOfWeek = LocalDate.now(clock).getDayOfWeek();

        List<Lesson> result = new ArrayList<>();

        for (int index = 0; index < quantity; index++) {
            Group group = randomUtils.getRandomElementFromList(groups);
            Course course = randomUtils.getRandomElementFromList(courses);
            String audience = randomUtils.getRandomElementFromList(audiences);
            LocalTime startTime = randomUtils.getStartTime();


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
}