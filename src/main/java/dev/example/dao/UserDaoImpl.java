package dev.example.dao;

import dev.example.dao.dto.UserDTO;
import dev.example.dao.dto.UserFullDTO;
import dev.example.dao.interfaces.UserDao;
import dev.example.entities.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
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
////                resultClass = dev.example.dao.dto.UserFullDTO.class
//)
//@javax.persistence.SqlResultSetMapping(
//        name = "UserDTO",
//        classes = @javax.persistence.ConstructorResult(
//                targetClass = dev.example.dao.dto.UserFullDTO.class,
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

        session.saveOrUpdate(user);
        transaction.commit();

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
    public UserDTO findById(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();


        final CriteriaBuilder builder = session.getCriteriaBuilder();
        final CriteriaQuery<UserDTO> query = builder.createQuery(UserDTO.class);
        final Root<User> root = query.from(User.class);

        //TODO: show manyToMany

//        Join<User, Role> userRoot = root.join(User_.ROLES);
       Join<User, List<Role>> userRoot = root.join(User_.ROLES);
//        Join<Role, List<User>> roleRoot = userRoot.join(Role_.USERS);
//
        Path<Long> idPath = root.get(User_.ID);
        Path<String> userNamePath = root.get(User_.USERNAME);
        Path<List<Role>> rolesPath = userRoot.get(User_.ROLES);
        Path<Address> addressesPath = root.get(User_.ADDRESSES);

        query
//                .select(
//                        builder.construct(
//                                UserDTO.class,
//                                idPath,
//                                userNamePath,
//                                rolesPath,
//                                addressesPath
//                        )
//                )
                .multiselect(
                        idPath,
                        userNamePath,
                        rolesPath,
                        addressesPath
                )
                .where(
                builder.equal(
                        root.get(User_.ID), id
                )
        )
        ;


//        final List<UserDTO> userDTO = session.createQuery(query).getResultList();
        final UserDTO userDTO = session.createQuery(query).getSingleResult();

        System.out.println(userDTO);

        log.info(userDTO);


//        Criteria criteria = session.createCriteria(User.class);
//        criteria.add(Restrictions.eq("id", id));
//        criteria.setProjection(Projections.projectionList()
//                .add(Projections.property("id"), "id")
//                .add(Projections.property("username"))
//                .add(Projections.property("roles"), "roles")
//                .add(Projections.property("addresses").as("addresses"))
//        );
//        criteria.setResultTransformer(new AliasToBeanResultTransformer(UserDTO.class));
//        List<UserDTO> user = criteria.list();


//        final CriteriaBuilder builder = session.getCriteriaBuilder();
//        final CriteriaQuery<User> query = builder.createQuery(User.class);
//        final Root<User> root = query.from(User.class);
//        query
//                .select(root)
//                .where(
//                        builder.equal(
//                                root.get("id"),
//                                id
//                        )
//                );
//        final Query<User> sessionQuery = session.createQuery(query);
//        final UserDTO user = sessionQuery
////                .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
////                .setResultTransformer(new AliasToBeanResultTransformer(UserDTO.class))
//                .getSingleResult();


//        final CriteriaQuery<UserDTO> select = query.where(
//                builder.equal(root.get("id"), id)
//        );
//        final Query<UserDTO> userDTOQuery = session.createQuery(select);
//        final UserDTO userDTO = userDTOQuery
////                .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
//                .getSingleResult();


//        final CriteriaQuery<UserDTO> criteriaQuery = query.where(builder.equal(root.get("id"), id));
//
//        final UserDTO userDTO = session.createQuery(criteriaQuery)
//                .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
//                .getSingleResult();


//        final UserDTO userDTO = session
//                .createQuery(
//                        query
//                                .select(root)
//                                .where(
//                                        builder.equal(
//                                                root.get("id"), id
//                                        )
//                                )
//                )
//
//                .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
//                .getSingleResult();

        transaction.commit();

//        return null;
        return userDTO;
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
