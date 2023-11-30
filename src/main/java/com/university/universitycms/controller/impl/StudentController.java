package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Student;
import com.university.universitycms.service.GroupService;
import com.university.universitycms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
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
        model.addAttribute("roles", Role.values());
        model.addAttribute("groups", groups);

        return "show-student";
    }

    @PostMapping("/students/update")
    public String updateStudentData(@RequestParam Long id, @RequestParam String name, @RequestParam String surname,
                                    @RequestParam String email, @RequestParam Long groupId){
        Group group = groupService.getGroupById(groupId).orElseThrow(() -> new IllegalArgumentException("Can't find group!"));
        Student student = studentService.getStudentById(id).orElseThrow(() -> new IllegalArgumentException("Can't find student!"));

        student.setName(name);
        student.setSurname(surname);
        student.setEmail(email);
        student.setGroup(group);

        studentService.updateStudent(student);

        return "redirect:/students";
    }
}
