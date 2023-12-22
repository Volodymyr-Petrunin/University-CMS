package com.university.universitycms.service;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.dto.GroupDTO;
import com.university.universitycms.domain.mapper.GroupMapper;
import com.university.universitycms.generation.impl.GroupGenerationData;
import com.university.universitycms.repository.GroupRepository;
import com.university.universitycms.filldata.DataFiller;
import com.university.universitycms.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class GroupService implements DataFiller {
    private final GroupRepository repository;
    private final StudentRepository studentRepository;
    private final GroupGenerationData groupGenerationData;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupService(GroupRepository repository, StudentRepository studentRepository, GroupGenerationData groupGenerationData,
                        GroupMapper groupMapper) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.groupGenerationData = groupGenerationData;
        this.groupMapper = groupMapper;
    }

    public List<Group> getAllGroups() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Group getGroupById(long groupId){
        return repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
    }

    public GroupDTO showGroupById(long id) {
        Group group = this.getGroupById(id);

        return groupMapper.groupToGroupDTO(group);
    }

    public void createGroup(Group group){
        repository.save(group);
    }

    public void createSeveralGroups(List<Group> groups){
        repository.saveAll(groups);
    }

    public void registerGroup(String groupName){
        this.createGroup(new Group(null, groupName, Collections.emptySet()));
    }

    public void updateGroup(GroupDTO group){
        repository.save(groupMapper.groupDTOToGroup(group));
    }

    public void graduateGroup(long id){
        repository.deleteById(id);
    }

    @Override
    public void fillData() {
        createSeveralGroups(groupGenerationData.generateData());
    }
}
