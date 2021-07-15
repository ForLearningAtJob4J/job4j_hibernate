package ru.job4j.hibernate.date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.TimeZone;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf
                    .withOptions()
//                    .jdbcTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
                    .openSession();
            List<Product> products = session.createQuery("from Product", Product.class).list();
            for (Product pr : products) {
                System.out.println(pr);
            }

//            session.beginTransaction();
//
//            Product pr = Product.of("Молоко", "Савушкин продукт");
//            session.save(pr);
//
//            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}