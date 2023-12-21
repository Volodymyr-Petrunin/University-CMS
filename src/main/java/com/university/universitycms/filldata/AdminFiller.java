package com.university.universitycms.filldata;

import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AdminFiller implements DataFiller {

    private final Teacher admin;
    private final String password;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TeacherRepository teacherRepository;

    public AdminFiller(TeacherRepository teacherRepository, @Value("${admin.name}") String name,
                       @Value("${admin.surname}") String surname, @Value("${admin.password}") String password,
                       @Value("${admin.email}") String email, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.teacherRepository = teacherRepository;
        this.password = password;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        this.admin = new Teacher(null, Role.ADMIN, name, surname, null, Collections.emptySet(), email);
    }

    @Override
    public void fillData() {
        admin.setPassword(bCryptPasswordEncoder.encode(password));

        teacherRepository.save(admin);
    }
}
