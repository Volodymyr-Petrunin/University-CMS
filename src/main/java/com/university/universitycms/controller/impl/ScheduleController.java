package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.*;
import com.university.universitycms.service.LessonService;
import com.university.universitycms.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private final LessonService lessonService;

    @Autowired
    public ScheduleController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/one-day-schedule")
    public String daySchedule(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        Map<String, List<Lesson>> lessons = lessonService.getLessonsByDayOfWeek(userDetailsImpl);

        model.addAttribute("oneDaySchedule", lessons);

        return "one-day-schedule";
    }

    @GetMapping("/week-schedule")
    public String weekSchedule(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        Map<String, List<Lesson>> lessons = lessonService.getLessonsForWeek(userDetailsImpl);

        model.addAttribute("weekSchedule", lessons);

        return "week-schedule";
    }

    @GetMapping("/month-schedule")
    public String monthSchedule(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        Map<String, List<Lesson>> lessonForMonth = lessonService.getLessonsForMonth(userDetailsImpl);

        model.addAttribute("monthSchedule", lessonForMonth);

        return "month-schedule";
    }
}
