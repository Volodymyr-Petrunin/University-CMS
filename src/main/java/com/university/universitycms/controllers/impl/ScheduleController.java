package com.university.universitycms.controllers.impl;

import com.university.universitycms.domain.Lesson;
import com.university.universitycms.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class ScheduleController {
    private final LessonService lessonService;
    private final LocalDate localDate;

    @Autowired
    public ScheduleController(LessonService lessonService, Clock clock) {
        this.lessonService = lessonService;
        this.localDate = LocalDate.now(clock);
    }

    @GetMapping("/one-day-schedule")
    public String daySchedule(Model model){
        Map<String, List<Lesson>> lessons = lessonService.getLessonsByDayOfWeek(localDate);

        model.addAttribute("oneDaySchedule", lessons);

        return "one-day-schedule";
    }

    @GetMapping("/week-schedule")
    public String weekSchedule(Model model){
        Map<String, List<Lesson>> lessons = lessonService.getLessonsForWeek();

        model.addAttribute("weekSchedule", lessons);

        return "week-schedule";
    }

    @GetMapping("/month-schedule")
    public String monthSchedule(Model model){
        Map<String, List<Lesson>> lessonForMonth = lessonService.getLessonForMonth();

        model.addAttribute("monthSchedule", lessonForMonth);

        return "month-schedule";
    }
}
