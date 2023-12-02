package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.*;
import com.university.universitycms.domain.dto.TeacherDTO;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TeacherRegistrationController {
    private final TeacherService teacherService;
    private final CourseService courseService;

    @Autowired
    public TeacherRegistrationController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @GetMapping("/register/teacher")
    public String registerUser(Model model){
        List<Course> courses = courseService.getAllCourses();

        model.addAttribute("roles", Role.values());
        model.addAttribute("courses", courses);

        return "register-teacher";
    }

    @PostMapping("/register/teacher")
    public String registerUser(@ModelAttribute TeacherDTO teacherDTO, @RequestParam List<Long> coursesId){
        teacherService.registerTeacher(teacherDTO, coursesId);
        return "redirect:/";
    }
}
