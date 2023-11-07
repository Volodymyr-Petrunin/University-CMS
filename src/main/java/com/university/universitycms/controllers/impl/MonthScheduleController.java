package com.university.universitycms.controllers.impl;

import com.university.universitycms.controllers.dataformatter.DateFormatter;
import com.university.universitycms.controllers.sortLessons.SortLessons;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MonthScheduleController {
    private final LessonService lessonService;
    private final DateFormatter dateFormatter;
    private final SortLessons sortLessons;
    private final List<DayOfWeek> dayOfWeeks;

    @Autowired
    public MonthScheduleController(LessonService lessonService, DateFormatter dateFormatter, SortLessons sortLessons) {
        this.lessonService = lessonService;
        this.dateFormatter = dateFormatter;
        this.sortLessons = sortLessons;

        this.dayOfWeeks = sortLessons.getDaysOfWeekForCurrentMonth();
    }

    @GetMapping("/month-schedule")
    public String monthSchedule(Model model){
        List<Lesson> allLessons = lessonService.getAllLessons();


        model.addAttribute("monthSchedule", monthDivideByWeeks(dateFormatter.formatDayAndDataMonthForWeekAndMonth(false),
                sortLessons.sortLessonByDayAndStartTimeForMonth(allLessons)));

        return "month-schedule";
    }

    private Map<String, List<Lesson>> monthDivideByWeeks(List<String> dataFormatted,List<Lesson> lessons){
        Map<String, List<Lesson>> result = new LinkedHashMap<>();

        int dayIndex = 0;

        for (String data : dataFormatted){
            int finalDayIndex = dayIndex;
            result.put(data, lessons.stream().filter(lesson -> lesson.getDayOfWeek().equals(dayOfWeeks.get(finalDayIndex))).toList());
            dayIndex++;

            if (dayIndex == 5){
                dayIndex = 0;
            }
        }

        return result;
    }
}
