package dev.example.dao;

import dev.example.entities.Organization;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class OrganizationDaoImpl {
    private final SessionFactory sessionFactory;

    @Autowired
    public OrganizationDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Organization organization){
        final Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(organization);
        session.flush();

        session.getTransaction().commit();
        session.close();

        log.info(organization);
    }
}
