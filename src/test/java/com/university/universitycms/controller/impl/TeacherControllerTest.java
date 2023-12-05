package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.domain.dto.TeacherDTO;
import com.university.universitycms.security.WebSecurityConfiguration;
import com.university.universitycms.service.CourseService;
import com.university.universitycms.service.TeacherService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeacherController.class)
@Import({WebSecurityConfiguration.class})
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TeacherService teacherService;
    @MockBean
    private CourseService courseService;

    private static final TeacherDTO expectedTeacherDTO = new TeacherDTO();

    private final Set<Course> expectedCourse = Set.of(
            new Course(1L, "IT"),
            new Course(2L, "Math")
    );

    private final List<Teacher> expectedTeachers = List.of(
            new Teacher(1L, Role.TEACHER, "Mr Vova", "Petrunin", null, expectedCourse, null),
            new Teacher(2L, Role.TEACHER, "Mr Oleg", "Nevagno", null, expectedCourse, null),
            new Teacher(3L, Role.TEACHER, "Mr Nestor", "Makhno", null, expectedCourse, null)
    );

    @BeforeAll
    static void setUppTeacherDto(){
        expectedTeacherDTO.setId(1L);
        expectedTeacherDTO.setName("Mr. Vanika");
        expectedTeacherDTO.setSurname("Big Lebowski");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testTeachers_ShouldRenderTeachers() throws Exception {
        when(teacherService.getAllTeachers()).thenReturn(expectedTeachers);

        String urlTemplate = "/teachers/all";
        String attributeName = "teachers";
        String viewName = "teachers";

        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(attributeName))
                .andExpect(model().attribute(attributeName, expectedTeachers))
                .andExpect(view().name(viewName));

        verify(teacherService, times(1)).getAllTeachers();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testOpenTeacherData_ShouldRenderShowTeacherPage() throws Exception {
        when(teacherService.getTeacherById(anyLong())).thenReturn(Optional.of(expectedTeachers.get(0)));
        when(courseService.getAllCourses()).thenReturn(expectedCourse.stream().toList());

        String urlTemplate = "/teachers/show/1";
        String teacherAttributeName = "teacher";
        String courseAttributeName = "courses";
        String viewName = "show-teacher";

        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(teacherAttributeName))
                .andExpect(model().attributeExists(courseAttributeName))
                .andExpect(view().name(viewName));

        verify(teacherService, times(1)).getTeacherById(anyLong());
        verify(courseService, times(1)).getAllCourses();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateTeacher_ShouldRedirectToAdminTeachers_AndCallUpdateTeacher() throws Exception {
        String urlTemplate = "/teachers/update";
        String attributeName = "teacherDTO";
        String redirectUrl = "/teachers/all";

        mockMvc.perform(post(urlTemplate)
                .flashAttr(attributeName, expectedTeacherDTO)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));

        verify(teacherService, times(1)).updateTeacher(expectedTeacherDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteTeacher_ShouldRedirectToAdminTeachers_AndCallDeleteTeacherById() throws Exception {
        String urlTemplate = "/teachers/delete";
        String attributeName = "deleteId";
        String redirectUrl = "/teachers/all";

        mockMvc.perform(post(urlTemplate)
                        .param(attributeName, String.valueOf(expectedTeacherDTO.getId()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));

        verify(teacherService, times(1)).deleteTeacherById(expectedTeacherDTO.getId());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void forbidRequestToAdmin_withStudentRole() throws Exception {
        mockMvc.perform(get("/teachers/show/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void forbidRequestToAdmin_withTeacherRole() throws Exception {
        mockMvc.perform(get("/teachers/show/1"))
                .andExpect(status().isForbidden());
    }
}