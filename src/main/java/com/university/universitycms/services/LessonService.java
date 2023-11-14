package com.university.universitycms.services;

import com.university.universitycms.domain.*;
import com.university.universitycms.generations.impl.LessonGenerationData;
import com.university.universitycms.repositories.LessonRepository;
import com.university.universitycms.services.datafilling.DataFiller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class LessonService implements DataFiller {
    private final LessonRepository repository;
    private final LessonGenerationData lessonGenerationData;
    private final Clock clock;

    @Autowired
    public LessonService(LessonRepository repository, LessonGenerationData lessonGenerationData, Clock clock) {
        this.repository = repository;
        this.lessonGenerationData = lessonGenerationData;
        this.clock = clock;
    }

    public List<Lesson> getAllLessons(){
        return repository.findAll();
    }

    public Optional<Lesson> getLessonById(long lessonId){
        return repository.findById(lessonId);
    }

    public void createLesson(Lesson lesson){
        repository.save(lesson);
    }

    public void createSeveralLessons(List<Lesson> lessons){
        repository.saveAll(lessons);
    }

    public void updateLesson(Lesson lesson){
        repository.save(lesson);
    }

    public void deleteLesson(Lesson lesson){
        repository.delete(lesson);
    }

    public Map<String, List<Lesson>> getLessonsByDayOfWeek(LocalDate localDate){
        List<Lesson> oneDayLesson = repository.findLessonByDayOfWeekOrderByStartTimeAsc(localDate.getDayOfWeek());
        String dayFormat = dataFormat(localDate);

        return Map.of(dayFormat, oneDayLesson);
    }

    public Map<String, List<Lesson>> getLessonsForWeek(){
        LocalDate firstDay = LocalDate.now(clock).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDay = firstDay.plusDays(4);

        List<Lesson> weekLesson = repository.findLessonByOrderByDayOfWeekAscStartTimeAsc();

        return createResultMap(firstDay, lastDay, weekLesson);
    }

    public Map<String, List<Lesson>> getLessonForMonth(){
        LocalDate firstDay = LocalDate.now(clock).withDayOfMonth(1);
        LocalDate lastDay = LocalDate.now(clock).with(TemporalAdjusters.lastDayOfMonth());

        List<Lesson> weekLesson = repository.findLessonByOrderByDayOfWeekAscStartTimeAsc();

        return createResultMap(firstDay, lastDay, weekLesson);
    }

    @Override
    public void fillData() {
        createSeveralLessons(lessonGenerationData.generateData());
    }

    private String dataFormat(LocalDate currentDay){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EE/dd/MM");

        return currentDay.format(formatter).toUpperCase();
    }

    private Map<String, List<Lesson>> createResultMap(LocalDate firstDay, LocalDate lastDay, List<Lesson> weekLesson){
        Map<String, List<Lesson>> result = new LinkedHashMap<>();

        for (LocalDate currentDay = firstDay; !currentDay.isAfter(lastDay); currentDay = currentDay.plusDays(1)){

            if (currentDay.getDayOfWeek().equals(DayOfWeek.SATURDAY) || currentDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                continue;
            }

            DayOfWeek day = currentDay.getDayOfWeek();
            List<Lesson> lessonsOfThisDay = weekLesson.stream()
                    .filter(lesson -> lesson.getDayOfWeek().equals(day))
                    .toList();

            result.put(dataFormat(currentDay), lessonsOfThisDay);
        }

        return result;
    }
}