package com.university.universitycms.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String name;
    private List<Long> coursesId;
}
