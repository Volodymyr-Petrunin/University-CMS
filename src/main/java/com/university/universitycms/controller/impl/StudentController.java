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
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public String students(Model model){
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);

        return "students";
    }

    @GetMapping("/show/{id}")
    public String openStudentData(@PathVariable(value = "id") long id, Model model){
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find student!"));

        List<Group> groups = groupService.getAllGroups();

        model.addAttribute("student", student);
        model.addAttribute("groups", groups);

        return "show-student";
    }

    @GetMapping("/register")
    public String registerStudent(Model model){
        List<Group> groups = groupService.getAllGroups();

        model.addAttribute("groups", groups);

        return "register-student";
    }

    @PostMapping("/register")
    public String registerStudent(@ModelAttribute StudentDTO studentDTO){
        studentService.registerStudent(studentDTO);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateStudentData(@ModelAttribute StudentDTO studentDTO){
        studentService.updateStudent(studentDTO);
        return "redirect:/students/all";
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam long deleteId){
        studentService.deleteStudentById(deleteId);
        return "redirect:/students/all";
    }
}
