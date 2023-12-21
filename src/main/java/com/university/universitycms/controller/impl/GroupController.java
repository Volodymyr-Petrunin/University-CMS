package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.dto.GroupDTO;
import com.university.universitycms.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public String groups(Model model) {
        List<Group> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);

        return "groups";
    }

    @GetMapping("/show/{id}")
    public String showGroup(@PathVariable long id, Model model) {
        GroupDTO groupDTO = groupService.showGroupDTOById(id);

        model.addAttribute("groupDTO", groupDTO);
        model.addAttribute("students", groupDTO.getStudents());
        return "show-group";
    }
}
