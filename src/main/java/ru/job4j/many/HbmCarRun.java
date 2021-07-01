package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmCarRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CarModel fiesta = CarModel.of("Fiesta");
            session.save(fiesta);
            CarModel focus = CarModel.of("Focus");
            session.save(focus);
            CarModel ecoSport = CarModel.of("EcoSport");
            session.save(ecoSport);
            CarModel mondeo = CarModel.of("Mondeo");
            session.save(mondeo);
            CarModel tourneoCustom = CarModel.of("Tourneo Custom");
            session.save(tourneoCustom);

            CarBrand ford = CarBrand.of("Ford");
            ford.addModel(fiesta);
            ford.addModel(focus);
            ford.addModel(ecoSport);
            ford.addModel(mondeo);
            ford.addModel(tourneoCustom);

            session.save(ford);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}