package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.dto.GroupDTO;
import com.university.universitycms.security.WebSecurityConfiguration;
import com.university.universitycms.service.GroupService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
@Import(WebSecurityConfiguration.class)
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    private static final GroupDTO groupDTO = new GroupDTO();

    private final Set<Group> expectedGroups = Set.of(
            new Group(1L, "ABC", Collections.emptySet()),
            new Group(2L, "CBA", Collections.emptySet()),
            new Group(3L, "IDK", Collections.emptySet())
    );

    @BeforeAll
    static void setUppTeacherDto(){
        groupDTO.setId(1L);
        groupDTO.setName("ABC");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testShowGroup_ShouldRedirectToGroupsAll_AndCallUpdateGroupMethod() throws Exception {
        String urlTemplate = "/groups/update";
        String attributeName = "groupDTO";
        String redirectUrl = "/groups/all";

        mockMvc.perform(post(urlTemplate)
                        .flashAttr(attributeName, groupDTO)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));

        verify(groupService, times(1)).updateGroup(groupDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGraduateGroup_ShouldRedirectToGroupsAll_AndCallGraduateGroupMethod() throws Exception {
        String urlTemplate = "/groups/graduate";
        String attributeName = "graduateId";
        String redirectUrl = "/groups/all";

        Long expectedId = 1L;

        mockMvc.perform(post(urlTemplate)
                        .param(attributeName, String.valueOf(expectedId))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));

        verify(groupService, times(1)).graduateGroup(expectedId);
    }


    @Test
    @WithMockUser(roles = "TEACHER")
    void forbidRequestToAdmin_withTeacherRole() throws Exception {
        mockMvc.perform(get("/groups/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void forbidRequestToAdmin_withStudentRole() throws Exception {
        mockMvc.perform(get("/groups/all"))
                .andExpect(status().isForbidden());
    }
}