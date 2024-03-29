package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.domain.dto.GroupDTO;
import com.university.universitycms.domain.dto.LessonDTO;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.GroupService;
import com.university.universitycms.service.LessonService;
import com.university.universitycms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.List;

@Controller
@RequestMapping("/lesson")
public class LessonController {
    private static final String REDIRECT_TO_LESSON_ALL = "redirect:/lesson/all";
    private final LessonService lessonService;
    private final EnumSet<DayOfWeek> dayOfWeeks;
    private final CourseService courseService;
    private final GroupService groupService;
    private final TeacherService teacherService;

    @Autowired
    public LessonController(LessonService lessonService, CourseService courseService, GroupService groupService, TeacherService teacherService) {
        this.lessonService = lessonService;
        this.courseService = courseService;
        this.groupService = groupService;
        this.teacherService = teacherService;
        this.dayOfWeeks = EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
    }

    @GetMapping("/all")
    public String lessons(Model model){
        List<Lesson> lessons = lessonService.getLessonsOrderByDayOfWeekAndStartTime();

        model.addAttribute("lessons", lessons);

        return "lessons";
    }


    @GetMapping("/register")
    public String registerLesson(Model model) {
        List<String> audiences = lessonService.getAllAudience();
        List<Course> courses = courseService.getAllCourses();
        List<GroupDTO> groups = groupService.getAllGroupsInDTO();
        List<Teacher> teachers = teacherService.getAllTeachers();

        model.addAttribute("audiences", audiences);
        model.addAttribute("dayOfWeeks", dayOfWeeks);
        model.addAttribute("courses", courses);
        model.addAttribute("groups", groups);
        // her we should use js for get teacher by course what admin chose (course.getTeachers())
        // but I have no idea how do this in html with bootstrap, so I suggest keep it like this
        model.addAttribute("teachers", teachers);

        return "register-lesson";
    }

    @PostMapping("/register")
    public String registerLesson(@ModelAttribute LessonDTO lessonDTO) {
        lessonService.registerLesson(lessonDTO);

        return REDIRECT_TO_LESSON_ALL;
    }

    @GetMapping("/show/{id}")
    public String showLesson(@PathVariable(name = "id") long lessonId, Model model){
        Lesson lesson = lessonService.getLessonById(lessonId);
        List<Course> courses = courseService.getAllCourses();
        List<GroupDTO> groups = groupService.getAllGroupsInDTO();
        List<String> audiences = lessonService.getAllAudience();

        model.addAttribute("lesson", lesson);
        model.addAttribute("dayOfWeeks", dayOfWeeks);
        model.addAttribute("courses", courses);
        model.addAttribute("groups", groups);
        model.addAttribute("audiences", audiences);

        // the same situation her we must use js for update course and teacher,
        //I suggest just keeping the same teacher

        return "show-lesson";
    }

    @PostMapping("/update")
    public String updateLesson(@ModelAttribute LessonDTO lessonDTO){
        lessonService.updateLesson(lessonDTO);
        return REDIRECT_TO_LESSON_ALL;
    }

    @PostMapping("/delete")
    public String deleteLesson(@RequestParam long lessonId){
        lessonService.deleteLessonById(lessonId);
        return REDIRECT_TO_LESSON_ALL;
    }
}
