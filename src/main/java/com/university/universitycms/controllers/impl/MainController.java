package com.university.universitycms.controllers.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model){
        List<String> title = List.of("Halloween", "New food in the school canteen", "IDK");
        model.addAttribute("newsList", title);
        return "index";
    }
}
