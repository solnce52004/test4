package dev.example.dao;

import dev.example.dao.interfaces.UserDao;
import dev.example.dto.UserFullDTO;
import dev.example.entities.Role;
import dev.example.entities.User;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        final Session session = sessionFactory.getCurrentSession();
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
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();

        session.remove(user);
        transaction.commit();

        log.info("user removed");
    }

    @Override
    public User findById(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();

        final User user = (User) session.createCriteria(User.class)
                .add(Restrictions.eq("id", id))
//                .setFetchMode("roles", FetchMode.JOIN)
//                .setFetchMode("addresses", FetchMode.JOIN)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list()
                .stream()
                .findFirst()
                .orElse(null);

        transaction.commit();

        //TODO
//        final UserDTO userDTO = new GetterTransformerUserToDto(sessionFactory).findById(id);

        return user;
    }

    @Override
    public List<User> findAll() {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();

        final Query<User> namedQuery = session.createNamedQuery("User_findUsersOrderByName");
        final List<User> namedQueryResultList = namedQuery
                .getResultList();

        transaction.commit();

        return namedQueryResultList;
    }

    @Override
    @SuppressWarnings("deprecated")
    public List<UserFullDTO> findAllByRole(Role role) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();

        List<UserFullDTO> namedQueryResultList = session
                .createNamedQuery("User_findUsersByRole_Native")
                .setParameter("role_title", role.getTitle())
                .unwrap(NativeQuery.class)
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("username", StandardBasicTypes.STRING)
                .addScalar("addressId", StandardBasicTypes.LONG)
                .addScalar("roleTitle", StandardBasicTypes.STRING)

                .setResultTransformer(Transformers.aliasToBean(UserFullDTO.class))
                .getResultList();

        transaction.commit();

        return namedQueryResultList;
    }
}
