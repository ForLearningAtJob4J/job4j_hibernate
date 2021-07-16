package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRunTask {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            session.createQuery("delete from Candidate")
                    .executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE IF EXISTS candidate_id_seq RESTART").executeUpdate();

            Candidate one = Candidate.of("Alex", 21, 3000.0);
            Candidate two = Candidate.of("Nikolay", 28, 4000.0);
            Candidate three = Candidate.of("Nikita", 25, 10_000.0);

            session.save(one);
            session.save(two);
            session.save(three);

            // всех кандидатов
            System.out.println("All:");
            session.createQuery("from Candidate ", Candidate.class).list().forEach(System.out::println);

            // кандидата по id
            System.out.println("By Id:");
            System.out.println(getById(session, 1));

            // кандидата по имени
            System.out.println("By Name:");
            System.out.println(
                    session.createQuery("from Candidate c where c.name = :fId", Candidate.class)
                            .setParameter("fId", "Nikolay").uniqueResult()
            );

            // обновления записи кандидата
            System.out.println("Update");
            System.out.println("Before:");
            System.out.println(getById(session, 1));
            session.createQuery("update Candidate c set c.experience = :newExp, c.salary = :newSalary where c.id = :fId")
                    .setParameter("newExp", 22)
                    .setParameter("newSalary", 3000.0)
                    .setParameter("fId", 2)
                    .executeUpdate();
            System.out.println("After:");
            System.out.println(getById(session, 2));

            // удаления записи кандидата по id
            System.out.println("Delete by Id:");
            session.createQuery("delete from Candidate where id = :fId")
                    .setParameter("fId", 1)
                    .executeUpdate();
            session.createQuery("from Candidate ", Candidate.class).list().forEach(System.out::println);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static Candidate getById(Session session, int id) {
        Query<Candidate> query = session.createQuery("from Candidate c where c.id = :fId", Candidate.class);
        query.setParameter("fId", id);
        return query.uniqueResult();
    }
}