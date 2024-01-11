package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Department;
import com.university.universitycms.domain.dto.DepartmentDTO;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private static final String REDIRECT_TO_DEPARTMENTS_ALL = "redirect:/departments/all";
    private final DepartmentService departmentService;
    private final CourseService courseService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, CourseService courseService) {
        this.departmentService = departmentService;
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public String departments(Model model){
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);

        return "departments";
    }

    @GetMapping("/show/{id}")
    public String showDepartment(@PathVariable(name = "id") long id, Model model){
        Department department = departmentService.getDepartmentById(id);
        List<Course> courses = courseService.getAllCourses();

        model.addAttribute("department", department);
        model.addAttribute("courses", courses);

        return "show-department";
    }

    @PostMapping("/update")
    public String updateDepartment(@ModelAttribute DepartmentDTO departmentDTO){
        departmentService.updateDepartment(departmentDTO);
        return REDIRECT_TO_DEPARTMENTS_ALL;
    }

    @PostMapping("/delete")
    public String deleteDepartment(@RequestParam long departmentId){
        departmentService.deleteDepartmentById(departmentId);
        return REDIRECT_TO_DEPARTMENTS_ALL;
    }

    @GetMapping("/register")
    public String registerDepartment(Model model){
        List<Course> courses = courseService.getAllCourses();

        model.addAttribute("courses", courses);

        return "register-department";
    }

    @PostMapping("/register")
    public String registerDepartment(@ModelAttribute DepartmentDTO departmentDTO){
        departmentService.registerDepartment(departmentDTO);
        return REDIRECT_TO_DEPARTMENTS_ALL;
    }
}
