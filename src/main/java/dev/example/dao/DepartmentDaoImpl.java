package dev.example.dao;

import dev.example.entities.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.RootGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import java.util.List;

@Repository
public class DepartmentDaoImpl {
    private final SessionFactory sessionFactory;

    @Autowired
    public DepartmentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Department findByIdWithGraph(Long id) {
        final Session session = sessionFactory.openSession();
        session.beginTransaction();

//        final User user = session.find(User.class, id);

//        Map<String, Object> properties = new HashMap<>();
        // статический

//        properties.put(
//                "javax.persistence.fetchgraph",
//                session.getEntityGraph("roles-graph")
//        );
//

        //динамический
//        EntityGraph<User> postGraph = session.createEntityGraph(User.class);
//        postGraph.addAttributeNodes("roles");
//        properties.put("javax.persistence.fetchgraph", postGraph);
//
//        final User user = session.find(User.class, id, properties);

        //указание в query
           final List<Department> list = session.createQuery(
                "select distinct d from departments d where d.id=:id",
                Department.class)
                .setParameter("id", id)
                .setHint(
                        "javax.persistence.fetchgraph",//1 JDBC statements
//                        "javax.persistence.loadgraph",//15 JDBC statements
                        session.getEntityGraph("department.organizations.users")
//                        session.getEntityGraph("department.organizations")
                )
                .getResultList();

        session.getTransaction().commit();
        session.close();

        return list
                .stream()
                .findFirst()
                .orElse(null);
    }
}
