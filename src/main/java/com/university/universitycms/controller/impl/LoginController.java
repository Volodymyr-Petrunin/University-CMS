package com.university.universitycms.controller.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Authentication authentication){
        return authentication != null ? "redirect:/" : "login";
    }
}
