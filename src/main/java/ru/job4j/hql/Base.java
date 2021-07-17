package ru.job4j.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
