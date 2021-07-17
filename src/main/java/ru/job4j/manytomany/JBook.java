package ru.job4j.manytomany;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "j_books")
public class JBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public static JBook of(String name) {
        JBook book = new JBook();
        book.name = name;
        return book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JBook book = (JBook) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}