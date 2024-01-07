package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@DiscriminatorValue("Teacher")
public class Teacher extends User {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teachers_course",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

    public Teacher() {

    }

    public Teacher(Long id, Role role, String name, String surname, String password, Set<Course> courses, String email) {
        super(id, role, name, surname, password, email);
        this.courses = courses;
    }

    public void addCourses(Course course) {
        this.courses.add(course);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + getId() +
                ", role=" + getRole() +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                '}';
    }
}
