package dev.example.dao;

import dev.example.dao.interfaces.UserDao;
import dev.example.dto.UserFullDTO;
import dev.example.entities.Role;
import dev.example.entities.User;
import dev.example.entities.filters.DynamicUserFilter;
import dev.example.entities.interceptors.AuditLogUserInterceptor;
import dev.example.entities.interceptors.UsernameValidationInterceptor;
import dev.example.entities.named_queries.UserNamedQueries;
import lombok.extern.log4j.Log4j2;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Log4j2
//@org.hibernate.annotations.NamedNativeQuery(
//        name = "User_findUsersByRole_Native",
//        query = "select " +
//                "u.id as id, " +
//                "u.username as username, " +
//                "u.address_id as addressTd, " +
//                "r.title as roleTitle " +
//                "from users u " +
//                "join user_role ur on u.id = ur.user_id " +
//                "join roles r on r.id = ur.role_id " +
//                "where r.title = :role_title",
//        resultSetMapping = "UserDTO"
////                resultClass = dev.example.dto.UserFullDTO.class
//)
//@javax.persistence.SqlResultSetMapping(
//        name = "UserDTO",
//        classes = @javax.persistence.ConstructorResult(
//                targetClass = dev.example.dto.UserFullDTO.class,
//                columns = {
//                        @javax.persistence.ColumnResult(name = "id"),
//                        @javax.persistence.ColumnResult(name = "username"),
//                        @javax.persistence.ColumnResult(name = "addressTd"),
//                        @javax.persistence.ColumnResult(name = "roleTitle")
//                }
//        )
//)
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long create(User user) {
        final Session session = sessionFactory
                .withOptions()
                .interceptor(new UsernameValidationInterceptor())
                .openSession();
        final Transaction transaction = session.beginTransaction();

        session.save(user);
        transaction.commit();
        session.close();

        final Long userId = user.getId();
        log.info("user:{} created", userId);
        return userId;
    }

    public Long createWithAudit(User user) {
//        final EntityManager em = sessionFactory.openSession();
//        Session session = em.unwrap(Session.class);
        final Session session = sessionFactory.openSession();
        final AuditLogUserInterceptor interceptor = (AuditLogUserInterceptor) ((SessionImplementor) session).getInterceptor();
        interceptor.setCurrentSession(session);
        interceptor.setCurrentAuthorId(15L);

        final Transaction transaction = session.beginTransaction();

        session.save(user);
        transaction.commit();

        session.close();

        final Long userId = user.getId();
        log.info("user:{} created", userId);
        return userId;
    }

    @Override
    public void remove(User user) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();

        session.remove(user);
        transaction.commit();
        session.close();
        log.info("user removed");
    }

    @Override
    public User findById(Long id) {
        final Session session = sessionFactory.openSession();
        session.beginTransaction();

        final List<User> list = session.createCriteria(User.class, "u")
                .add(Restrictions.eq("u.id", id))
                .setFetchMode("roles", FetchMode.JOIN)
                .setFetchMode("addresses", FetchMode.JOIN)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();

        final Transaction transaction = session.getTransaction();
        System.out.println(transaction);

        session.getTransaction().commit();
        session.close();


        return list
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();

        final Query<User> namedQuery = session.createNamedQuery(UserNamedQueries.FIND_USERS_ORDER_BY_NAME);
        final List<User> namedQueryResultList = namedQuery
                .getResultList();

        transaction.commit();
        session.close();

        return namedQueryResultList;
    }

    public List<User> findAllByIsActualByCreatedAt() {

        final Session session = sessionFactory.openSession();
        session.beginTransaction();
        final Filter filter = session.enableFilter(DynamicUserFilter.LIMIT_BY_IS_ACTUAL);

        filter.setParameter(DynamicUserFilter.LIMIT_BY_IS_ACTUAL_PARAM_CREATED_AT, "2021-10-14");
        filter.setParameter(DynamicUserFilter.LIMIT_BY_IS_ACTUAL_PARAM_IS_ACTUAL, 4);

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        query.select(query.from(User.class));
        final List<User> list = session.createQuery(query).getResultList();

//        final List<User> list = session
//                .createQuery("select u from User u", User.class)
//                .getResultList();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    @Override
    @SuppressWarnings("deprecated")
    public List<UserFullDTO> findAllByRole(Role role) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();

        List<UserFullDTO> namedQueryResultList = session
                .createNamedQuery(UserNamedQueries.FIND_USERS_BY_ROLE_NATIVE)
                .setParameter("role_title", role.getTitle())
                .unwrap(NativeQuery.class)
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("username", StandardBasicTypes.STRING)
                .addScalar("addressId", StandardBasicTypes.LONG)
                .addScalar("roleTitle", StandardBasicTypes.STRING)

                .setResultTransformer(Transformers.aliasToBean(UserFullDTO.class))
                .getResultList();

        transaction.commit();
        session.close();

        return namedQueryResultList;
    }

    public void update(User user) {
        Session session = sessionFactory
                .withOptions()
                .interceptor(new UsernameValidationInterceptor())
                .openSession();
        session.beginTransaction();

        session.update(user);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        final User userExists = session.get(User.class, user.getId());
        session.remove(userExists);

        session.getTransaction().commit();
        session.close();
    }
}
