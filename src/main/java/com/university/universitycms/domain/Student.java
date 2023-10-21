package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("Student")
public class Student extends User {

    @ManyToOne
    @JoinColumn(name = "student_group_id")
    private Group group;

    public Student() {

    }

    public Student(Long id, Role role, String name, String surname, String password) {
        super(id, role, name, surname, password);
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
