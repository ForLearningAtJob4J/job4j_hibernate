package ru.job4j.many;

import javax.persistence.*;

@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public static CarModel of(String name) {
        CarModel carModel = new CarModel();
        carModel.name = name;
        return carModel;
    }
}
