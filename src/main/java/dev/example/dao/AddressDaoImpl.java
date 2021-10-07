package dev.example.dao;

import dev.example.dao.interfaces.AddressDao;
import dev.example.entities.Address;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class AddressDaoImpl implements AddressDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public AddressDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Address address) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();

        session.save(address);
        log.info("address created");
        transaction.commit();
    }
}
