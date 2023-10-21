package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("Teacher")
public class Teacher extends User {

    @ManyToMany
    @JoinTable(
            name = "teachers_course",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    public void addCourses(Course course) {
        if (courses == null){
            courses = new ArrayList<>();
        }
        this.courses.add(course);
    }

    public Teacher() {

    }

    public Teacher(Long id, Role role, String name, String surname, String password) {
        super(id, role, name, surname, password);
    }
}
