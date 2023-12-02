package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.dto.StudentDTO;
import com.university.universitycms.service.GroupService;
import com.university.universitycms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class StudentRegistrationController {
    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentRegistrationController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping("/register/student")
    public String registerStudent(Model model){
        List<Group> groups = groupService.getAllGroups();

        model.addAttribute("groups", groups);

        return "register-student";
    }

    @PostMapping("/register/student")
    public String registerStudent(@ModelAttribute StudentDTO studentDTO, long groupId){
        studentService.registerStudent(studentDTO, groupId);
        return "redirect:/";
    }
}
