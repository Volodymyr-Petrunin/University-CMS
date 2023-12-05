package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.domain.dto.TeacherDTO;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final EnumSet<Role> roles;

    @Autowired
    public TeacherController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.roles = EnumSet.of(Role.TEACHER, Role.ADMIN);
    }

    @GetMapping("/all")
    public String teachers(Model model){
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);

        return "teachers";
    }

    @GetMapping("/show/{id}")
    public String openTeacherData(@PathVariable(value = "id") long id, Model model){
        Teacher teacher = teacherService.getTeacherById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find teacher!"));

        List<Course> courses = courseService.getAllCourses();

        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", courses);
        model.addAttribute("roles", roles);

        return "show-teacher";
    }

    @GetMapping("/register")
    public String registerTeacher(Model model){
        List<Course> courses = courseService.getAllCourses();

        model.addAttribute("roles", roles);
        model.addAttribute("courses", courses);

        return "register-teacher";
    }

    @PostMapping("/register")
    public String registerTeacher(@ModelAttribute TeacherDTO teacherDTO){
        teacherService.registerTeacher(teacherDTO);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateTeacherData(@ModelAttribute TeacherDTO teacherDTO){
        teacherService.updateTeacher(teacherDTO);
        return "redirect:/teachers/all";
    }

    @PostMapping("/delete")
    public String deleteTeacher(@RequestParam long deleteId){
        teacherService.deleteTeacherById(deleteId);
        return "redirect:/teachers/all";
    }
}
