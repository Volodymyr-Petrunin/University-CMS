package com.university.universitycms.service;

import com.university.universitycms.domain.*;
import com.university.universitycms.generation.impl.LessonGenerationData;
import com.university.universitycms.repository.LessonRepository;
import com.university.universitycms.filldata.DataFiller;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@Transactional
public class LessonService implements DataFiller {
    private final LessonRepository repository;
    private final LessonGenerationData lessonGenerationData;
    private final Clock clock;
    private final DateTimeFormatter formatter;

    @Autowired
    public LessonService(LessonRepository repository, LessonGenerationData lessonGenerationData, Clock clock,
                         DateTimeFormatter formatter) {
        this.repository = repository;
        this.lessonGenerationData = lessonGenerationData;
        this.clock = clock;
        this.formatter = formatter;
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

    public Map<String, List<Lesson>> getLessonsByDayOfWeek(UserDetailsImpl userDetails){

        if (userDetails.checkRole(Role.STUDENT)){
            return getLessonsByDayOfWeekAndGroupForStudent(userDetails.unwrap(Student.class));
        }

        return getLessonsByDayOfWeekAndCourseForTeacher(userDetails.unwrap(Teacher.class));
    }

    private Map<String, List<Lesson>> getLessonsByDayOfWeekAndGroupForStudent(Student student) {
        LocalDate today = LocalDate.now(clock);

        if (isWeekend(today)) {
            today = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        List<Lesson> oneDayLesson = repository
                .findLessonByDayOfWeekAndGroupOrderByStartTimeAsc(today.getDayOfWeek(), student.getGroup());

        String dayFormat = formatDate(today);

        return Map.of(dayFormat, oneDayLesson);
    }

    private Map<String, List<Lesson>> getLessonsByDayOfWeekAndCourseForTeacher(Teacher teacher){
        LocalDate today = LocalDate.now(clock);

        if (isWeekend(today)) {
            today = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        List<Lesson> oneDayLesson = repository
                .findLessonsByDayOfWeekAndCourseInOrderByStartTimeAsc(today.getDayOfWeek(), teacher.getCourses());

        String dayFormat = formatDate(today);

        return Map.of(dayFormat, oneDayLesson);
    }

    public Map<String, List<Lesson>> getLessonsForWeek(UserDetailsImpl userDetailsImpl){

        if (userDetailsImpl.checkRole(Role.STUDENT)){
            return getLessonsForWeekForStudent(userDetailsImpl.unwrap(Student.class));
        }

        return getLessonsForWeekForTeacher(userDetailsImpl.unwrap(Teacher.class));
    }

    private Map<String, List<Lesson>> getLessonsForWeekForStudent(Student student){
        LocalDate firstDay = LocalDate.now(clock).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDay = firstDay.plusDays(4);

        List<Lesson> weekLesson = repository.findLessonsByGroupOrderByDayOfWeekAscStartTimeAsc(student.getGroup());

        return createResultMap(firstDay, lastDay, weekLesson);
    }

    private Map<String, List<Lesson>> getLessonsForWeekForTeacher(Teacher teacher){
        LocalDate firstDay = LocalDate.now(clock).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDay = firstDay.plusDays(4);

        List<Lesson> weekLesson = repository.findLessonsByCourseInOrderByDayOfWeekAscStartTimeAsc(teacher.getCourses());

        return createResultMap(firstDay, lastDay, weekLesson);
    }

    public Map<String, List<Lesson>> getLessonsForMonth(UserDetailsImpl userDetailsImpl){

        if (userDetailsImpl.checkRole(Role.STUDENT)){
            return getLessonsForMonthForStudent(userDetailsImpl.unwrap(Student.class));
        }

        return getLessonsForMonthForTeacher(userDetailsImpl.unwrap(Teacher.class));
    }

    private Map<String, List<Lesson>> getLessonsForMonthForStudent(Student student){
        LocalDate firstDay = LocalDate.now(clock).withDayOfMonth(1);
        LocalDate lastDay = LocalDate.now(clock).with(TemporalAdjusters.lastDayOfMonth());

        List<Lesson> weekLesson = repository.findLessonsByGroupOrderByDayOfWeekAscStartTimeAsc(student.getGroup());

        return createResultMap(firstDay, lastDay, weekLesson);
    }

    private Map<String, List<Lesson>> getLessonsForMonthForTeacher(Teacher teacher){
        LocalDate firstDay = LocalDate.now(clock).withDayOfMonth(1);
        LocalDate lastDay = LocalDate.now(clock).with(TemporalAdjusters.lastDayOfMonth());

        List<Lesson> weekLesson = repository.findLessonsByCourseInOrderByDayOfWeekAscStartTimeAsc(teacher.getCourses());

        return createResultMap(firstDay, lastDay, weekLesson);
    }

    @Override
    public void fillData() {
        createSeveralLessons(lessonGenerationData.generateData());
    }

    private String formatDate(LocalDate date){
        return date.format(formatter).toUpperCase();
    }

    private boolean isWeekend(LocalDate date){
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
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

            result.put(formatDate(currentDay), lessonsOfThisDay);
        }

        return result;
    }
}