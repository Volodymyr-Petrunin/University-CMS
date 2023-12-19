package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@DiscriminatorValue("Teacher")
public class Teacher extends User {

    @ManyToMany
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(courses, teacher.courses);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + getId() +
                ", role=" + getRole() +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
