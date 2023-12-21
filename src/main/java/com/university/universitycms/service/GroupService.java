package com.university.universitycms.service;

import com.university.universitycms.domain.Group;
import com.university.universitycms.generation.impl.GroupGenerationData;
import com.university.universitycms.repository.GroupRepository;
import com.university.universitycms.filldata.DataFiller;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupService implements DataFiller {
    private final GroupRepository repository;
    private final GroupGenerationData groupGenerationData;

    @Autowired
    public GroupService(GroupRepository repository, GroupGenerationData groupGenerationData) {
        this.repository = repository;
        this.groupGenerationData = groupGenerationData;
    }

    public List<Group> getAllGroups() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Group getGroupById(long groupId){
        return repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
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

    public void graduateGroup(Group group){
        repository.delete(group);
    }

    @Override
    public void fillData() {
        createSeveralGroups(groupGenerationData.generateData());
    }
}
