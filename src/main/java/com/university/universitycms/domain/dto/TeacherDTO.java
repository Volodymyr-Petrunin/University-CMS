package com.university.universitycms.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO extends UserDTO {
    private List<Long> coursesId;
}
