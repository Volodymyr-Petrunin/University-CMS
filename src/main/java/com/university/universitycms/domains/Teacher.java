package com.university.universitycms.domains;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Teacher extends User {

    @ManyToMany
    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourses(Course course) {
        this.courses.add(course);
    }
}
