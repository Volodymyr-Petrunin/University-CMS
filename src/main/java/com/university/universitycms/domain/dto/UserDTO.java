package com.university.universitycms.domain.dto;

import com.university.universitycms.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private Role role;
    private String name;
    private String surname;
    private String password;
    private String email;
}
