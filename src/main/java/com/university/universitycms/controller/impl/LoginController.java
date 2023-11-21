package com.university.universitycms.controller.impl;

import com.university.universitycms.service.StudentService;
import com.university.universitycms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String surname, @RequestParam String password) {
        return "redirect:/";
    }
}
