package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@DiscriminatorValue("Student")
public class Student extends User {

    @ManyToOne
    @JoinColumn(name = "student_group_id")
    private Group group;

    public Student() {

    }

    public Student(Long id, Role role, String name, String surname, String password, Group group, String email) {
        super(id, role, name, surname, password, email);
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", role=" + getRole() +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", password='" + getPassword() + '\'' +
                "} group=" + group +
                '}';
    }
}
