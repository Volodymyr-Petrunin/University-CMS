package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String courses(Model model){
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);

        return "courses";
    }
}
