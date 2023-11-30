package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.*;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.GroupService;
import com.university.universitycms.service.StudentService;
import com.university.universitycms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final GroupService groupService;
    private final CourseService courseService;

    @Autowired
    public AdminController(StudentService studentService, TeacherService teacherService, GroupService groupService, CourseService courseService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.groupService = groupService;
        this.courseService = courseService;
    }

    @GetMapping("/reg")
    public String registerUser(Model model){
        List<Group> groups = groupService.getAllGroups();
        List<Course> courses = courseService.getAllCourses();

        model.addAttribute("roles", Role.values());
        model.addAttribute("groups", groups);
        model.addAttribute("courses", courses);

        return "register-user";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String surname,
                               @RequestParam String email, @RequestParam Role role,
                               @RequestParam(required = false) Long groupId,
                               @RequestParam(required = false) Long courseId){

        if (role.equals(Role.STUDENT)){
            Group group = groupService.getGroupById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Group not found with id " + groupId));

            studentService.createStudent(new Student(null, role, name, surname, null, group, email));
        }

        // we need this for check for create admin without course
        Course course = (courseId != null) ? courseService.getCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id " + courseId)) : null;

        teacherService.createTeacher(new Teacher(null, role, name, surname, null, Collections.singleton(course), email));

        return "redirect:/";
    }
}
