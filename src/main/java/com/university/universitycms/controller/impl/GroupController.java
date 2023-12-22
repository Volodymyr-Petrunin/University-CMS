package com.university.universitycms.controller.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.dto.GroupDTO;
import com.university.universitycms.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private static final String REDIRECT_TO_GROUPS_ALL = "redirect:/groups/all";
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
        GroupDTO groupDTO = groupService.getDTOById(id);

        model.addAttribute("groupDTO", groupDTO);
        return "show-group";
    }

    @PostMapping("/update")
    public String updateGroup(@ModelAttribute GroupDTO groupDTO){
        groupService.updateGroup(groupDTO);
        return REDIRECT_TO_GROUPS_ALL;
    }

    @PostMapping("/graduate")
    public String graduateGroup(@RequestParam long graduateId){
        groupService.graduateGroup(graduateId);
        return REDIRECT_TO_GROUPS_ALL;
    }

    @GetMapping("/register")
    public String registerGroup(){
        return "register-group";
    }

    @PostMapping("/register")
    public String registerGroup(@RequestParam String groupName){
        groupService.registerGroup(groupName);
        return REDIRECT_TO_GROUPS_ALL;
    }
}
