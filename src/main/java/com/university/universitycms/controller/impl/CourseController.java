package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private static final String REDIRECT_TO_COURSES_ALL = "redirect:/courses/all";
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public String courses(Model model){
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);

        return "courses";
    }

    @GetMapping("/register")
    public String registerCourse(){
        return "register-course";
    }

    @PostMapping("/register")
    public String registerCourse(@RequestParam String courseName){
        courseService.registerCourse(courseName);
        return REDIRECT_TO_COURSES_ALL;
    }

    @GetMapping("/show/{id}")
    public String openCourseData(@PathVariable(value = "id") long id, Model model){
        Course course = courseService.getCourseById(id);

        model.addAttribute("course", course);

        return "show-course";
    }

    @PostMapping("/update")
    public String updateCourse(@ModelAttribute Course course){
        courseService.updateCourse(course);
        return REDIRECT_TO_COURSES_ALL;
    }

    @PostMapping("/delete")
    public String deleteCourse(@RequestParam long deleteId){
        courseService.deleteCourseById(deleteId);
        return REDIRECT_TO_COURSES_ALL;
    }
}
