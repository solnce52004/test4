package dev.example.dao.criteria;

import dev.example.dto.UserDTO;
import dev.example.entities.User;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetterTransformerUserToDto {

    private final SessionFactory sessionFactory;

    @Autowired
    public GetterTransformerUserToDto(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserDTO findById(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.beginTransaction();

        //TODO: Could not resolve PropertyAccess for this on class dev.example.dto.UserDTO
        final List list = session.createCriteria(User.class)
                .add(Restrictions.eq("id", id))
//                .createAlias("u.roles", "roles")
//                .createAlias("u.addresses", "addresses")
//                .setProjection(
//                        Projections.projectionList()
//                                .add(Property.forName("u.id").as("id"))
//                                .add(Property.forName("u.username").as("username"))
//                                .add(Property.forName("roles").as("roles"))
//                                .add(Property.forName("addresses").as("addresses"))
//                                .add(Projections.property("addresses"), "addresses")
//                )

                .setFetchMode("roles", FetchMode.JOIN)
                .setFetchMode("addresses", FetchMode.JOIN)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
//                .setResultTransformer(new AliasToBeanResultTransformer(UserDTO.class))
//               .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
                .list();

        System.out.println(list);
//        final Object user = list.stream().findFirst().orElse(null);
//        System.out.println(user.toString());

//        final UserDTO user = list.stream().findFirst().orElse(null);
//        System.out.println(user);

        transaction.commit();
        return null;
//        return userDTO;
    }

//    @Override
//    public UserDTO findById(Long id) {
//        final Session session = sessionFactory.getCurrentSession();
//        final Transaction transaction = session.beginTransaction();
//
//
//        final CriteriaBuilder builder = session.getCriteriaBuilder();
//        final CriteriaQuery<UserDTO> query = builder.createQuery(UserDTO.class);
//        final Root<User> root = query.from(User.class);
//
//        //TODO: show manyToMany
//
////        Join<User, Role> userRoot = root.join(User_.ROLES);
//       Join<User, List<Role>> userRoot = root.join(User_.ROLES);
////        Join<Role, List<User>> roleRoot = userRoot.join(Role_.USERS);
////
//        Path<Long> idPath = root.get(User_.ID);
//        Path<String> userNamePath = root.get(User_.USERNAME);
//        Path<List<Role>> rolesPath = userRoot.get(User_.ROLES);
//        Path<Address> addressesPath = root.get(User_.ADDRESSES);
//
//        query
////                .select(
////                        builder.construct(
////                                UserDTO.class,
////                                idPath,
////                                userNamePath,
////                                rolesPath,
////                                addressesPath
////                        )
////                )
//                .multiselect(
//                        idPath,
//                        userNamePath,
//                        rolesPath,
//                        addressesPath
//                )
//                .where(
//                builder.equal(
//                        root.get(User_.ID), id
//                )
//        );
//
//        final UserDTO userDTO = session.createQuery(query).getSingleResult();
//
//        System.out.println(userDTO);
//        log.info(userDTO);
//
//
////        Criteria criteria = session.createCriteria(User.class);
////        criteria.add(Restrictions.eq("id", id));
////        criteria.setProjection(Projections.projectionList()
////                .add(Projections.property("id"), "id")
////                .add(Projections.property("username"))
////                .add(Projections.property("roles"), "roles")
////                .add(Projections.property("addresses").as("addresses"))
////        );
////        criteria.setResultTransformer(new AliasToBeanResultTransformer(UserDTO.class));
////        List<UserDTO> user = criteria.list();
//
//
////        final CriteriaBuilder builder = session.getCriteriaBuilder();
////        final CriteriaQuery<User> query = builder.createQuery(User.class);
////        final Root<User> root = query.from(User.class);
////        query
////                .select(root)
////                .where(
////                        builder.equal(
////                                root.get("id"),
////                                id
////                        )
////                );
////        final Query<User> sessionQuery = session.createQuery(query);
////        final UserDTO user = sessionQuery
//////                .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
//////                .setResultTransformer(new AliasToBeanResultTransformer(UserDTO.class))
////                .getSingleResult();
//
//
////        final CriteriaQuery<UserDTO> select = query.where(
////                builder.equal(root.get("id"), id)
////        );
////        final Query<UserDTO> userDTOQuery = session.createQuery(select);
////        final UserDTO userDTO = userDTOQuery
//////                .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
////                .getSingleResult();
//
//
////        final CriteriaQuery<UserDTO> criteriaQuery = query.where(builder.equal(root.get("id"), id));
////
////        final UserDTO userDTO = session.createQuery(criteriaQuery)
////                .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
////                .getSingleResult();
//
//
////        final UserDTO userDTO = session
////                .createQuery(
////                        query
////                                .select(root)
////                                .where(
////                                        builder.equal(
////                                                root.get("id"), id
////                                        )
////                                )
////                )
////
////                .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
////                .getSingleResult();
//
//        transaction.commit();
//
////        return null;
//        return userDTO;
//    }

}
