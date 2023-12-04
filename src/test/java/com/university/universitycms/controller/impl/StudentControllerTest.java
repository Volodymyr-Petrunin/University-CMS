package com.university.universitycms.controller.impl;

import com.university.universitycms.controller.impl.StudentController;
import com.university.universitycms.domain.*;
import com.university.universitycms.service.GroupService;
import com.university.universitycms.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    @MockBean
    private GroupService groupService;

    private final Group expectedGroup = new Group(1L, "A12");

    private final List<Student> expectedStudents = List.of(
      new Student(1L, Role.STUDENT, "Vova", "Petrunin", null, expectedGroup, null),
      new Student(2L, Role.STUDENT, "Oleg", "Nevagno", null, expectedGroup, null),
      new Student(3L, Role.STUDENT, "Nestor", "Makhno", null, expectedGroup, null)
    );

    @Test
    void testStudents_ShouldRenderStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(expectedStudents);

        String urlTemplate = "/students";
        String attributeName = "students";
        String viewName = "students";

        mockMvc.perform(get("/admin" + urlTemplate).with(user("user").password("password")))
                .andDo(print())
                .andExpect(model().attributeExists(attributeName))
                .andExpect(model().attribute(attributeName, expectedStudents))
                .andExpect(view().name(viewName));

        verify(studentService, times(1)).getAllStudents();
    }
}