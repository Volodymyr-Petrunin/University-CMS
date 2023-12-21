package com.university.universitycms.domain.dto;

import com.university.universitycms.domain.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class GroupDTO {
    private Long id;
    private String name;
    private Set<Student> students;
}
