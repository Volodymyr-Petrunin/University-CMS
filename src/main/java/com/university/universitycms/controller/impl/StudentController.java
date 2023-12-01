package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Student;
import com.university.universitycms.domain.dto.StudentDTO;
import com.university.universitycms.service.GroupService;
import com.university.universitycms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class StudentController {
    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping("/students")
    public String students(Model model){
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);

        return "students";
    }

    @GetMapping("/students/{id}")
    public String openStudentData(@PathVariable(value = "id") Long id, Model model){
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't get student!"));

        List<Group> groups = groupService.getAllGroups();

        model.addAttribute("student", student);
        model.addAttribute("groups", groups);

        return "show-student";
    }

    @PostMapping("/students/update")
    public String updateStudentData(@ModelAttribute StudentDTO studentDTO, @RequestParam long groupId){
        studentService.updateStudent(studentDTO, groupId);
        return "redirect:/admin/students";
    }
}
