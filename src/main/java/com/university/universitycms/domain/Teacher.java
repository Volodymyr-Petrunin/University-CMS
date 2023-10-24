package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@DiscriminatorValue("Teacher")
public class Teacher extends User {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teachers_course",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    public void addCourses(Course course) {
        this.courses.add(course);
    }

    public Teacher() {

    }

    public Teacher(Long id, Role role, String name, String surname, String password, List<Course> courses) {
        super(id, role, name, surname, password);
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(courses, teacher.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), courses);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + getId() +
                ", role=" + getRole() +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", password='" + getPassword() + '\'' +
                "} courses=" + courses +
                '}';
    }
}
