package com.university.universitycms.filldata;

import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.repository.TeacherRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FillData {
    private final List<DataFiller> dataFillers;
    private final TeacherRepository teacherRepository;

    @Autowired
    public FillData(List<DataFiller> dataFillers, TeacherRepository teacherRepository) {
        this.dataFillers = dataFillers;
        this.teacherRepository = teacherRepository;
    }

//    @PostConstruct
    public void fill(){
        dataFillers.forEach(DataFiller::fillData);

        // create admin
        // message for mentor password 123 :) I forbid everyone else to use this password :)))
        Teacher admin = new Teacher(null, Role.ADMIN, null, null,
                "$2a$12$HbvHND2vGbfkg4C4PbEBQe1vi5XjDscDeXDPEY4gAo/LPMu4yOQs2",
                Collections.emptySet(), "admin@.com");

        teacherRepository.save(admin);
    }
}
