package dev.example.dao;

import dev.example.dao.interfaces.RoleDao;
import dev.example.entities.Role;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class RoleDaoImpl implements RoleDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Role role) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();

        session.save(role);
        log.info("role created");
        transaction.commit();
    }
}
