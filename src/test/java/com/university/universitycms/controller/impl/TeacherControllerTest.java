package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;
    @MockBean
    private CourseService courseService;

    private final Set<Course> expectedCourse = Set.of(
            new Course(1L, "IT"),
            new Course(2L, "Math")
    );

    private final List<Teacher> expectedTeachers = List.of(
            new Teacher(1L, Role.TEACHER, "Mr Vova", "Petrunin", null, expectedCourse, null),
            new Teacher(2L, Role.TEACHER, "Mr Oleg", "Nevagno", null, expectedCourse, null),
            new Teacher(3L, Role.TEACHER, "Mr Nestor", "Makhno", null, expectedCourse, null)
    );

    @Test
    void testTeachers_ShouldRenderTeachers() throws Exception {
        when(teacherService.getAllTeachers()).thenReturn(expectedTeachers);

        String urlTemplate = "/teachers";
        String attributeName = "teachers";
        String viewName = "teachers";

        mockMvc.perform(get("/admin" + urlTemplate).with(user("user").password("password")))
                .andDo(print())
                .andExpect(model().attributeExists(attributeName))
                .andExpect(model().attribute(attributeName, expectedTeachers))
                .andExpect(view().name(viewName));

        verify(teacherService, times(1)).getAllTeachers();
    }

}