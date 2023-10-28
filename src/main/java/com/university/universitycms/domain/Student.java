package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Getter
@DiscriminatorValue("Student")
public class Student extends User {

    @ManyToOne
    @JoinColumn(name = "student_group_id")
    private Group group;

    public Student() {

    }

    public Student(Long id, Role role, String name, String surname, String password, Group group) {
        super(id, role, name, surname, password);
        this.group = group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), group);
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
