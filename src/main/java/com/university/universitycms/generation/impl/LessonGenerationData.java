package com.university.universitycms.generation.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.generation.GenerationData;
import com.university.universitycms.reader.ResourcesFileReader;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LessonGenerationData implements GenerationData<Lesson> {
    private final GenerationRandomizer generationRandomizer;
    private final Clock clock;
    private final GroupService groupService;
    private final CourseService courseService;
    private final ResourcesFileReader resourcesFileReader;
    private final String audienceFile;
    private final int quantity;

    @Autowired
    public LessonGenerationData(GenerationRandomizer generationRandomizer, Clock clock, GroupService groupService, CourseService courseService,
                                ResourcesFileReader resourcesFileReader,
                                @Value("${generation.file.audiences}") String audienceFile,
                                @Value("${quantity.max.lessonInWeek}") int quantity) {
        this.generationRandomizer = generationRandomizer;
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
        List<Course> courses = courseService.getAllCourses();
        DayOfWeek currentDayOfWeek = LocalDate.now(clock).getDayOfWeek();

        List<Lesson> result = new ArrayList<>();

        for (int index = 0; index < quantity; index++) {
            Group group = generationRandomizer.getRandomElementFromList(groups);
            Course course = generationRandomizer.getRandomElementFromList(courses);
            String audience = generationRandomizer.getRandomElementFromList(audiences);
            LocalTime startTime = generationRandomizer.getStartTime();
            currentDayOfWeek = getDayOfWeek(index, currentDayOfWeek);

            result.add(new Lesson(null, createLessonName(course, group), audience, currentDayOfWeek, startTime,
                    startTime.plusHours(2), course, group));
        }

        return result;
    }

    private String createLessonName(Course course, Group group){
        return course.getName() + " group " + group.getName();
    }

    private DayOfWeek getDayOfWeek(int index, DayOfWeek currentDayOfWeek){
        if (index % 5 == 0) {
            currentDayOfWeek = currentDayOfWeek.plus(1);
        }

        if (currentDayOfWeek == DayOfWeek.SATURDAY) {
            currentDayOfWeek = DayOfWeek.MONDAY;
        }

        return currentDayOfWeek;
    }
}