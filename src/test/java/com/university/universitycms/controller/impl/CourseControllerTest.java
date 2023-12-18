package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.security.WebSecurityConfiguration;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
@Import(WebSecurityConfiguration.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;
    @MockBean
    private TeacherService teacherService;

    private final List<Course> expectedCourse = List.of(
            new Course(1L, "IT", null),
            new Course(2L, "Math", null)
    );

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateCourse_ShouldRedirectToCoursesAll_AndCallTeacherUpdateAndCourseUpdateMethods() throws Exception {

        String urlTemplate = "/courses/update";
        String courseAttributeName = "course";
        String listAttributeName = "teachersId";
        String redirectUrl = "/courses/all";

        Course course = expectedCourse.get(1);
        Long expectedId = 1L;

        mockMvc.perform(post(urlTemplate)
                    .flashAttr(courseAttributeName, course)
                    .param(listAttributeName, String.valueOf(expectedId))
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));

        verify(courseService, times(1)).updateCourse(course, Collections.singleton(expectedId));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteCourse_ShouldRedirectToCoursesAll_AndCallDeleteCourseByIdMethod() throws Exception {
        String urlTemplate = "/courses/delete";
        String attributeName = "deleteId";
        String redirectUrl = "/courses/all";

        Long expectedId = 1L;

        mockMvc.perform(post(urlTemplate)
                        .param(attributeName, String.valueOf(expectedId))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));

        verify(courseService, times(1)).deleteCourseById(expectedId);
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void forbidRequestToAdmin_withTeacherRole() throws Exception {
        mockMvc.perform(get("/courses/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void forbidRequestToAdmin_withStudentRole() throws Exception {
        mockMvc.perform(get("/courses/all"))
                .andExpect(status().isForbidden());
    }
}