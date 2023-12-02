package com.university.universitycms.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO extends UserDTO {
    private Set<CourseDTO> courses;
}
