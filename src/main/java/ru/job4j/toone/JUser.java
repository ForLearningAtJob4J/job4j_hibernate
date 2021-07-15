package ru.job4j.toone;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "j_user")
public class JUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private JRole role;

    public static JUser of(String name, JRole role) {
        JUser user = new JUser();
        user.name = name;
        user.role = role;
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JRole getRole() {
        return role;
    }

    public void setRole(JRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JUser user = (JUser) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}