package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private static final String REDIRECT_TO_COURSES_ALL = "redirect:/courses/all";
    private final CourseService courseService;
    private final TeacherService teacherService;

    @Autowired
    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping("/all")
    public String courses(Model model){
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);

        return "courses";
    }

    @GetMapping("/register")
    public String registerCourse(Model model){
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);

        return "register-course";
    }

    @PostMapping("/register")
    public String registerCourse(@RequestParam String courseName, @RequestParam Set<Long> teachersId){
        courseService.registerCourse(courseName, teachersId);
        return REDIRECT_TO_COURSES_ALL;
    }

    @GetMapping("/show/{id}")
    public String openCourseData(@PathVariable(value = "id") long id, Model model){
        Course course = courseService.getCourseById(id);
        Set<Teacher> teachers = new HashSet<>(teacherService.getAllTeachers());

        Set<Teacher> teachersInCourse = course.getTeachers();

        model.addAttribute("course", course);
        model.addAttribute("teachers", teachers);
        model.addAttribute("teachersIn", teachersInCourse);

        return "show-course";
    }

    @PostMapping("/update")
    public String updateCourse(@ModelAttribute Course course, @RequestParam Set<Long> teachersId){
        courseService.updateCourse(course, teachersId);
        return REDIRECT_TO_COURSES_ALL;
    }

    @PostMapping("/delete")
    public String deleteCourse(@RequestParam long deleteId){
        courseService.deleteCourseById(deleteId);
        return REDIRECT_TO_COURSES_ALL;
    }
}
