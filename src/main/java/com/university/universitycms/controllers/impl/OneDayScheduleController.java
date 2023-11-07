package com.university.universitycms.controllers.impl;

import com.university.universitycms.controllers.dataformatter.DateFormatter;
import com.university.universitycms.controllers.sortLessons.SortLessons;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OneDayScheduleController {
    private final LessonService lessonService;
    private final SortLessons sortLessons;
    private final DateFormatter dateFormatter;

    @Autowired
    public OneDayScheduleController(LessonService lessonService, SortLessons sortLessons, DateFormatter dateFormatter) {
        this.lessonService = lessonService;
        this.sortLessons = sortLessons;
        this.dateFormatter = dateFormatter;
    }

    @GetMapping("/one-day-schedule")
    public String daySchedule(Model model){
        List<Lesson> lessons = lessonService.getAllLessons();

        model.addAttribute("dateFormatter", dateFormatter.formatDayAndDataForOneDay());
        model.addAttribute("lessons", sortLessons.sortedLessonByDayAndStartTimeForOneDay(lessons, dateFormatter.getLocalDay().getDayOfWeek()));

        return "one-day-schedule";
    }
}
