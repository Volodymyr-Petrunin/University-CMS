package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 100)
    private Long id;
    @Transient
    private Role role;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_surname")
    private String surname;
    @Column(name = "user_password")
    private String password;

    protected User() {

    }

    protected User(Long id, Role role, String name, String surname, String password) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && role == user.role && Objects.equals(name, user.name)
                && Objects.equals(surname, user.surname) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, name, surname, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
