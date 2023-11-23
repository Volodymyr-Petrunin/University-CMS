package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Department;
import com.university.universitycms.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public String departments(Model model){
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);

        return "departments";
    }
}
