package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Student;
import com.university.universitycms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String students(Model model){
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);

        return "students";
    }
}
