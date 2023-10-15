package com.university.universitycms.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Student extends User {

    @OneToOne
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
