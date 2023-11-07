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
import java.util.EnumSet;
import java.util.List;

@Controller
public class WeekScheduleController {
    private final EnumSet<DayOfWeek> dayOfWeeks = EnumSet.allOf(DayOfWeek.class);
    private final LessonService lessonService;
    private final DateFormatter dateFormatter;
    private final SortLessons sortLessons;

    @Autowired
    public WeekScheduleController(LessonService lessonService, DateFormatter dateFormatter, SortLessons sortLessons) {
        this.lessonService = lessonService;
        this.dateFormatter = dateFormatter;
        this.sortLessons = sortLessons;

        dayOfWeeks.remove(DayOfWeek.SATURDAY);
        dayOfWeeks.remove(DayOfWeek.SUNDAY);
    }

    @GetMapping("/week-schedule")
    public String weekSchedule(Model model){
        List<Lesson> allLessons = lessonService.getAllLessons();

        model.addAttribute("dateFormatter", dateFormatter.formatDayAndDataMonthForWeekAndMonth(true));
        model.addAttribute("daysOfWeek", dayOfWeeks);
        model.addAttribute("lessons", sortLessons.sortLessonByDayAndStartTimeForWeek(allLessons));

        return "week-schedule";
    }
}
