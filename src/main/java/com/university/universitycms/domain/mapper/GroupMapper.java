package com.university.universitycms.domain.mapper;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.dto.GroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = StudentMapper.class)
public interface GroupMapper {
    GroupDTO groupToGroupDTO(Group group);
    Group groupDTOToGroup(GroupDTO groupDTO);
}
