package dev.example.dao.audit;

import dev.example.entities.User;
import dev.example.entities.envers.CustomRevisionEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class AuditUserDaoImpl {
    private final SessionFactory sessionFactory;

    @Autowired
    public AuditUserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Map<Number, CustomRevisionEntity> findRevisionsByNumbers(Set<Number> numbers){
        final Session session = sessionFactory.openSession();
        final AuditReader reader = AuditReaderFactory.get(session);

        final Map<Number, CustomRevisionEntity> revisions = reader.findRevisions(CustomRevisionEntity.class, numbers);

        session.close();
        return revisions;
    }

    public List<Number> getUserRevisions(Long userId) {
        final Session session = sessionFactory.openSession();
        final AuditReader reader = AuditReaderFactory.get(session);

        final List<Number> revisions = reader.getRevisions(User.class, userId);

        session.close();
        return revisions;
    }

    //TODO: try tracking
    public void getRev() {
        final Session session = sessionFactory.openSession();
        final AuditReader reader = AuditReaderFactory.get(session);

        final AuditQuery query = reader
                .createQuery()
                .forEntitiesAtRevision(User.class, 5023);

        final List list = query.getResultList();

        list.forEach(System.out::println);


//        final Set<ModifiedEntityTypeEntity> types = reader
//                .findRevision(CustomTrackingRevisionEntity.class, 5023)
//                .getModifiedEntityTypes();
//
//        types.forEach(System.out::println);


//        query.add( AuditEntity.property( "name" ).eq(  "John" ) );

        session.close();
    }
}
