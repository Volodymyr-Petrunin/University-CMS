package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Lesson;
import com.university.universitycms.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ScheduleController {
    private final LessonService lessonService;

    @Autowired
    public ScheduleController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/one-day-schedule")
    public String daySchedule(Model model){
        Map<String, List<Lesson>> lessons = lessonService.getLessonsByDayOfWeek();

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
        Map<String, List<Lesson>> lessonForMonth = lessonService.getLessonsForMonth();

        model.addAttribute("monthSchedule", lessonForMonth);

        return "month-schedule";
    }
}
