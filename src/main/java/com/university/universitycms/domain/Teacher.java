package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Teacher extends User {

    @ManyToMany
    private List<Course> courses;

    public void addCourses(Course course) {
        this.courses.add(course);
    }
}
