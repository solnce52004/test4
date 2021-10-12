package dev.example;

import dev.conf.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App {

    private static final Logger log = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

        log.info("App start");


//        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration()
////                .addPackage("dev.example.entities")
////                .setProperty("mapping", "dev.example.entities")
//                .addAnnotatedClass(Users.class)
//                .buildSessionFactory();

        //---CREATE----
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        Users users = new Users("d3424g34d", User.DEF_ROLE);
//        session.save(users);
//        session.getTransaction().commit();
//        session.close();

        //---READ----
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        final Users users = session.get(Users.class, 1L);
//        System.out.println(users);
//        session.getTransaction().commit();
//        session.close();

        //---READ--HQL--
        //вместо названия таблицы - название класса сущности
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//
//        final Users id = session.createQuery(
//                "SELECT u FROM Users u WHERE id = :id",
//                Users.class)
//                .setParameter("id", 2L)
//                .getSingleResult();
//
//        System.out.println(id);
//
//        final List<Users> usersList = session.createQuery(
//                "SELECT u FROM Users u",
//                Users.class)
//                .getResultList();
//
//        System.out.println(usersList);
//
//        session.getTransaction().commit();
//        session.close();

        //---UPDATE----
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        final Users users = session.get(Users.class, 1L);
//        System.out.println(users);
//        users.setRole("eee");
////        session.flush();
//        session.getTransaction().commit();
//        session.close();

        //---DELETE----
//        Session session = sessionFactory.getCurrentSession();
//        session.beginTransaction();
//        final Users users = session.get(Users.class, 1L);
//        session.remove(users);
//        session.getTransaction().commit();
//        session.close();
    }
}
