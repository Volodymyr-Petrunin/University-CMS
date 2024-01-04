package com.university.universitycms.controller.impl;

import com.university.universitycms.controller.impl.StudentController;
import com.university.universitycms.domain.*;
import com.university.universitycms.domain.dto.StudentDTO;
import com.university.universitycms.security.WebSecurityConfiguration;
import com.university.universitycms.service.GroupService;
import com.university.universitycms.service.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@Import({WebSecurityConfiguration.class})
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    @MockBean
    private GroupService groupService;
    private final static StudentDTO expectedStudentDTO = new StudentDTO();

    private final Group expectedGroup = new Group(1L, "A12", Collections.emptySet());

    private final List<Student> expectedStudents = List.of(
      new Student(1L, Role.STUDENT, "Vova", "Petrunin", null, expectedGroup, null),
      new Student(2L, Role.STUDENT, "Oleg", "Nevagno", null, expectedGroup, null),
      new Student(3L, Role.STUDENT, "Nestor", "Makhno", null, expectedGroup, null)
    );

    @BeforeAll
    static void setUppTeacherDto(){
        expectedStudentDTO.setId(1L);
        expectedStudentDTO.setName("Vanika");
        expectedStudentDTO.setSurname("Lebowski");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testStudents_ShouldRenderStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(expectedStudents);

        String urlTemplate = "/students/all";
        String attributeName = "students";
        String viewName = "students";

        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(model().attributeExists(attributeName))
                .andExpect(model().attribute(attributeName, expectedStudents))
                .andExpect(view().name(viewName));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testOpenStudentData_ShouldRenderShowStudentPage() throws Exception {
        when(studentService.getStudentById(anyLong())).thenReturn(Optional.of(expectedStudents.get(0)));
        when(groupService.getAllGroups()).thenReturn(Collections.singletonList(expectedGroup));

        String urlTemplate = "/students/show/1";
        String studentsAttributeName = "student";
        String groupsAttributeName = "groups";
        String viewName = "show-student";

        mockMvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(studentsAttributeName))
                .andExpect(model().attributeExists(groupsAttributeName))
                .andExpect(view().name(viewName));

        verify(studentService, times(1)).getStudentById(anyLong());
        verify(groupService, times(1)).getAllGroups();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateStudent_ShouldRedirectToStudentsAll_AndCallUpdateStudent() throws Exception {
        String urlTemplate = "/students/update";
        String attributeName = "studentDTO";
        String redirectUrl = "/students/all";

        mockMvc.perform(post(urlTemplate)
                .flashAttr(attributeName, expectedStudentDTO)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));

        verify(studentService, times(1)).updateStudent(expectedStudentDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteStudent_ShouldRedirectToStudentsAll_AndCallDeleteStudentById() throws Exception {
        String urlTemplate = "/students/delete";
        String attributeName = "deleteId";
        String redirectUrl = "/students/all";

        long id = expectedStudentDTO.getId();

        mockMvc.perform(post(urlTemplate)
                        .param(attributeName, String.valueOf(id))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));

        verify(studentService, times(1)).deleteStudentById(id);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void forbidRequestToAdmin_withStudentRole() throws Exception {
        mockMvc.perform(get("/students/show/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void forbidRequestToAdmin_withTeacherRole() throws Exception {
        mockMvc.perform(get("/students/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void expectOkRequestToAdmin_withAdminRole() throws Exception {
        mockMvc.perform(get("/students/all"))
                .andExpect(status().isOk());
    }
}