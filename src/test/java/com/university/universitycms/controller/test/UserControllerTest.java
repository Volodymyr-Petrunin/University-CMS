package com.university.universitycms.controller.test;

import com.university.universitycms.controller.impl.UserController;
import com.university.universitycms.domain.*;
import com.university.universitycms.service.StudentService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private TeacherService teacherService;

    private final Group expectedGroup = new Group(1L, "A12");

    private final Set<Course> expectedCourse = Set.of(
            new Course(1L, "IT"),
            new Course(2L, "Math")
    );

    private final List<Student> expectedStudents = List.of(
      new Student(1L, Role.STUDENT, "Vova", "Petrunin", null, expectedGroup),
      new Student(2L, Role.STUDENT, "Oleg", "Nevagno", null, expectedGroup),
      new Student(3L, Role.STUDENT, "Nestor", "Makhno", null, expectedGroup)
    );

    private final List<Teacher> expectedTeachers = List.of(
            new Teacher(1L, Role.TEACHER, "Mr Vova", "Petrunin", null, expectedCourse),
            new Teacher(2L, Role.TEACHER, "Mr Oleg", "Nevagno", null, expectedCourse),
            new Teacher(3L, Role.TEACHER, "Mr Nestor", "Makhno", null, expectedCourse)
    );

    @Test
    void testStudents_ShouldRenderStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(expectedStudents);

        String urlTemplate = "/students";
        String attributeName = "students";
        String viewName = "students";

        mockMvc.perform(get(urlTemplate).with(user("user").password("password")))
                .andDo(print())
                .andExpect(model().attributeExists(attributeName))
                .andExpect(model().attribute(attributeName, expectedStudents))
                .andExpect(view().name(viewName));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testTeachers_ShouldRenderTeachers() throws Exception {
        when(teacherService.getAllTeachers()).thenReturn(expectedTeachers);

        String urlTemplate = "/teachers";
        String attributeName = "teachers";
        String viewName = "teachers";

        mockMvc.perform(get(urlTemplate).with(user("user").password("password")))
                .andDo(print())
                .andExpect(model().attributeExists(attributeName))
                .andExpect(model().attribute(attributeName, expectedTeachers))
                .andExpect(view().name(viewName));

        verify(teacherService, times(1)).getAllTeachers();
    }
}