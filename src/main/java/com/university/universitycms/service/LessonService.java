package com.university.universitycms.service;

import com.university.universitycms.domain.*;
import com.university.universitycms.domain.dto.LessonDTO;
import com.university.universitycms.domain.mapper.LessonMapper;
import com.university.universitycms.generation.impl.LessonGenerationData;
import com.university.universitycms.reader.ResourcesFileReader;
import com.university.universitycms.repository.LessonRepository;
import com.university.universitycms.filldata.DataFiller;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@Transactional
@Order(6)
public class LessonService implements DataFiller {
    private final LessonRepository repository;
    private final LessonGenerationData lessonGenerationData;
    private final Clock clock;
    private final DateTimeFormatter formatter;
    private final LessonMapper lessonMapper;
    private final String audienceFile;
    private final ResourcesFileReader resourcesFileReader;

    @Autowired
    public LessonService(LessonRepository repository, LessonGenerationData lessonGenerationData, Clock clock,
                         DateTimeFormatter formatter, LessonMapper lessonMapper,
                         @Value("${generation.file.audiences}") String audienceFile, ResourcesFileReader resourcesFileReader) {
        this.repository = repository;
        this.lessonGenerationData = lessonGenerationData;
        this.clock = clock;
        this.formatter = formatter;
        this.lessonMapper = lessonMapper;
        this.audienceFile = audienceFile;
        this.resourcesFileReader = resourcesFileReader;
    }

    public List<Lesson> getAllLessons(){
        return repository.findAll();
    }

    public List<Lesson> getLessonsOrderByDayOfWeekAndStartTime(){
        return repository.findLessonsByOrderByDayOfWeekAscStartTimeAsc();
    }

    public List<String> getAllAudience(){
        return resourcesFileReader.read(audienceFile);
    }

    public Lesson getLessonById(long lessonId){
        return repository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Cant get lesson with id: " + lessonId));
    }

    public void createLesson(Lesson lesson){
        repository.save(lesson);
    }

    public void createSeveralLessons(List<Lesson> lessons){
        repository.saveAll(lessons);
    }

    public void registerLesson(LessonDTO lessonDTO) {
        Lesson lesson = lessonMapper.lessonDTOToLesson(lessonDTO);

        validateLessonAvailability(lesson);

        this.createLesson(lesson);
    }

    public void updateLesson(LessonDTO lessonDTO){
        repository.save(lessonMapper.lessonDTOToLesson(lessonDTO));
    }

    public void deleteLesson(Lesson lesson){
        repository.delete(lesson);
    }

    public void deleteLessonById(long id){
        repository.deleteById(id);
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
                .findLessonsByDayOfWeekAndTeacherOrderByStartTimeAsc(today.getDayOfWeek(), teacher);

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

        List<Lesson> weekLesson = repository.findLessonsByTeacherOrderByDayOfWeekAscStartTimeAsc(teacher);

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

        List<Lesson> weekLesson = repository.findLessonsByTeacherOrderByDayOfWeekAscStartTimeAsc(teacher);

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

    private void validateLessonAvailability(Lesson lesson) {
        if (!audienceIsFree(lesson))
            throw new IllegalArgumentException("Audience: " + lesson.getAudience() + ", is not free at this time. " + lesson.getStartTime() + "-" + lesson.getEndTime());
        else if (!groupIsFree(lesson))
            throw new IllegalArgumentException("Group: " + lesson.getGroup().getName() +  ", is not free at this time.");
        else if (!teacherIsFree(lesson))
            throw new IllegalArgumentException("Teacher: " + lesson.getTeacher() + ", is not free at this time.");
    }

    private boolean audienceIsFree(Lesson lesson) {
        return !repository.existsAllByAudienceAndStartTimeBetweenAndDayOfWeek(
                lesson.getAudience(),
                lesson.getStartTime(),
                lesson.getEndTime(),
                lesson.getDayOfWeek()
        );
    }

    private boolean groupIsFree(Lesson lesson) {
        return !repository.existsAllByGroupAndStartTimeBetweenAndDayOfWeek(
                lesson.getGroup(),
                lesson.getStartTime(),
                lesson.getEndTime(),
                lesson.getDayOfWeek()
        );
    }

    private boolean teacherIsFree(Lesson lesson) {
        return !repository.existsAllByTeacherAndStartTimeBetweenAndDayOfWeek(
                lesson.getTeacher(),
                lesson.getStartTime(),
                lesson.getEndTime(),
                lesson.getDayOfWeek()
        );
    }
}