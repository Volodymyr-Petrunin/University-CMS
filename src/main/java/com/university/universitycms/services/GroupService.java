package com.university.universitycms.services;

import com.university.universitycms.domains.Group;
import com.university.universitycms.repositories.GroupRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GroupService {
    private final GroupRepo repository;

    @Autowired
    public GroupService(GroupRepo repository) {
        this.repository = repository;
    }

    public List<Group> getAllGroups() {
        return repository.findAll();
    }

    public void createGroup(Group group){
        repository.save(group);
    }

    public void createSeveralGroups(List<Group> groups){
        repository.saveAll(groups);
    }

    public void updateGroup(Group group){
        repository.save(group);
    }

    public void deleteGroup(Group group){
        repository.delete(group);
    }

}
