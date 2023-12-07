package com.university.universitycms.domain;

public enum Role {
    STUDENT,
    TEACHER,
    ADMIN;

    public String getRoleName() {
        return "ROLE_" + this.name();
    }
}
