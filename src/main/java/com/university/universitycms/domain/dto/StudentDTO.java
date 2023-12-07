package com.university.universitycms.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StudentDTO extends UserDTO{
    private Long groupId;
}
