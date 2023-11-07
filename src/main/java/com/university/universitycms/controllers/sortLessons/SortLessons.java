package com.university.universitycms.controllers.sortLessons;

import com.university.universitycms.domain.Lesson;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SortLessons {

    public List<Lesson> sortedLessonByDayAndStartTimeForOneDay(List<Lesson> lessons, DayOfWeek day){
        return lessons.stream()
                .filter(lesson -> lesson.getDayOfWeek().equals(day))
                .sorted(Comparator.comparing(Lesson::getStartTime))
                .toList();
    }

    public Map<DayOfWeek, List<Lesson>> sortLessonByDayAndStartTimeForWeek(List<Lesson> lessons){
       return lessons.stream().sorted(Comparator.comparing(Lesson::getStartTime))
               .collect(Collectors.groupingBy(Lesson::getDayOfWeek));
    }

    public List<Lesson> sortLessonByDayAndStartTimeForMonth(List<Lesson> lessons) {
        List<Lesson> result = new ArrayList<>();

        List<DayOfWeek> dayOfWeeks = getDaysOfWeekForCurrentMonth();

        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            List<Lesson> lessonByOneDay = sortedLessonByDayAndStartTimeForOneDay(lessons, dayOfWeek);
            result.addAll(lessonByOneDay);
        }

        return result;
    }

    public List<DayOfWeek> getDaysOfWeekForCurrentMonth(){
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);

        List<DayOfWeek> currentWeekDays = new ArrayList<>();

        if (firstDayOfMonth.getDayOfWeek() == DayOfWeek.MONDAY){
            currentWeekDays = new ArrayList<>(EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        } else {
            currentWeekDays.addAll(EnumSet.range(firstDayOfMonth.getDayOfWeek(), DayOfWeek.FRIDAY));

            currentWeekDays.addAll(EnumSet.range(DayOfWeek.MONDAY, firstDayOfMonth.getDayOfWeek().minus(1)));
        }

        return currentWeekDays;
    }
}
