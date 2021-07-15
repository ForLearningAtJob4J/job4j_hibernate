package ru.job4j.toone;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "j_role")
public class JRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public static JRole of(String name) {
        JRole role = new JRole();
        role.name = name;
        return role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JRole role = (JRole) o;
        return id == role.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}