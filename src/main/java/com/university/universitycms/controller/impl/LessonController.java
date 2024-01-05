package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.domain.dto.GroupDTO;
import com.university.universitycms.domain.dto.LessonDTO;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.GroupService;
import com.university.universitycms.service.LessonService;
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

    @Autowired
    public LessonController(LessonService lessonService, CourseService courseService, GroupService groupService) {
        this.lessonService = lessonService;
        this.courseService = courseService;
        this.groupService = groupService;
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

        model.addAttribute("audiences", audiences);
        model.addAttribute("dayOfWeeks", dayOfWeeks);
        model.addAttribute("courses", courses);
        model.addAttribute("groups", groups);

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
