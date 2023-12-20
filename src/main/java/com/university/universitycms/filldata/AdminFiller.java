package com.university.universitycms.filldata;

import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.repository.TeacherRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Profile("create-admin")
public class AdminFiller implements DataFiller {

    private final TeacherRepository teacherRepository;

    public AdminFiller(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    @PostConstruct
    public void fillData() {
        // message for mentor password 123 :) I forbid everyone else to use this password :)))
        Teacher admin = new Teacher(null, Role.ADMIN, "admin", "admin",
                "$2a$12$HbvHND2vGbfkg4C4PbEBQe1vi5XjDscDeXDPEY4gAo/LPMu4yOQs2",
                Collections.emptySet(), "admin@.com");

        teacherRepository.save(admin);
    }
}
