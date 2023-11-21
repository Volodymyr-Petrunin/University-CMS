package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Student;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.service.StudentService;
import com.university.universitycms.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private final StudentService studentService;
    private final TeacherService teacherService;

    public UserController(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @GetMapping("/students")
    public String students(Model model){
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);

        return "students";
    }

    @GetMapping("/teachers")
    public String teachers(Model model){
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);

        return "teachers";
    }
}
