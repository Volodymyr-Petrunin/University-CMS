package com.university.universitycms.filldata;

import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.repository.TeacherRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Profile("create-admin")
public class AdminFiller implements DataFiller {

    private final Teacher admin;

    private final TeacherRepository teacherRepository;

    public AdminFiller(TeacherRepository teacherRepository, @Value("${admin.name}") String name,
                       @Value("${admin.surname}") String surname, @Value("${admin.cashed.password}") String password,
                       @Value("${admin.email}") String email) {
        this.teacherRepository = teacherRepository;

        // message for mentor password 123 :) I forbid everyone else to use this password :)))
        this.admin = new Teacher(null, Role.ADMIN, name, surname, password, Collections.emptySet(), email);
    }

    @Override
    @PostConstruct
    public void fillData() {
        teacherRepository.save(admin);
    }
}
