package ru.job4j.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmCarLazyFetchRun {
    public static void main(String[] args) {
        List<Brand> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//            Session session = sf.openSession();
//            session.beginTransaction();

//            Brand ford = Brand.of("Ford");
//
//            ford.addModel(Model.of("Fiesta"));
//            ford.addModel(Model.of("Focus"));
//            ford.addModel(Model.of("EcoSport"));
//            ford.addModel(Model.of("Mondeo"));
//            ford.addModel(Model.of("Tourneo Custom"));
//            session.persist(ford);
//
//            session.getTransaction().commit();
//            session.close();

            Session session2 = sf.openSession();
            session2.beginTransaction();
            list = session2.createQuery(
                    "select distinct b from Brand b join fetch b.models", Brand.class
            ).list();
            session2.getTransaction().commit();
            session2.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Model model : list.get(0).getModels()) {
            System.out.println(model);
        }
    }
}