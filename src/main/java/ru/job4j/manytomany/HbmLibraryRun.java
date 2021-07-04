package ru.job4j.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmLibraryRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book nightPatrol = Book.of("Ночной дозор");
            Book dayPatrol = Book.of("Дневной дозор");

            Author sergeyLukiyanenko = Author.of("Сергей Лукьяненко");
            sergeyLukiyanenko.addBook(nightPatrol);
            sergeyLukiyanenko.addBook(dayPatrol);

            Author vladimirVasiliev = Author.of("Владимир Васильев");
            vladimirVasiliev.addBook(nightPatrol);

            session.persist(sergeyLukiyanenko);
            session.persist(vladimirVasiliev);

            Author author = session.get(Author.class, 1);
            session.remove(author);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}