package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.TeacherService;
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
        List<Teacher> teachers = teacherService.getAllTeachers();

        List<Teacher> teachersInCourse = teachers.stream()
                .filter(teacher -> teacher.getCourses().contains(course))
                .toList();

        model.addAttribute("course", course);
        model.addAttribute("teachers", teachers);
        model.addAttribute("teachersIn", teachersInCourse);

        return "show-course";
    }

    @PostMapping("/update")
    public String updateCourse(@ModelAttribute Course course, @RequestParam List<Long> teachersId){
        teacherService.addTeachersToCourse(teachersId, course);
        courseService.updateCourse(course);
        return REDIRECT_TO_COURSES_ALL;
    }

    @PostMapping("/delete")
    public String deleteCourse(@RequestParam long deleteId){
        courseService.deleteCourseById(deleteId);
        return REDIRECT_TO_COURSES_ALL;
    }
}
