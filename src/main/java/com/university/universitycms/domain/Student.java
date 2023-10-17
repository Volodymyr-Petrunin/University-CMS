package com.university.universitycms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Student extends User {

    @OneToOne
    private Group group;

    public void setGroup(Group group) {
        this.group = group;
    }
}
