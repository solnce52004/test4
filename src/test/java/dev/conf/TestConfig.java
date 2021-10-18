package dev.conf;

import dev.example.dao.*;
import dev.example.dao.audit.AuditUserDaoImpl;
import dev.example.dao.interfaces.UserDao;
import dev.example.services.UserService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
@Import(TestHibernateConfig.class)
public class TestConfig {

    @Bean
    public DepartmentDaoImpl departmentDaoImpl(SessionFactory sessionFactory){
        return new DepartmentDaoImpl(sessionFactory);
    }

    @Bean
    public AuditUserDaoImpl auditUserDao(SessionFactory sessionFactory){
        return new AuditUserDaoImpl(sessionFactory);
    }

    @Bean
    public OrganizationDaoImpl organizationDao(SessionFactory sessionFactory){
        return new OrganizationDaoImpl(sessionFactory);
    }

    @Bean
    public UserDaoImpl userDaoImpl(SessionFactory sessionFactory){
        return new UserDaoImpl(sessionFactory);
    }

    @Bean
    public AddressDaoImpl addressDao(SessionFactory sessionFactory){
        return new AddressDaoImpl(sessionFactory);
    }

    @Bean
    public RoleDaoImpl roleDao(SessionFactory sessionFactory){
        return new RoleDaoImpl(sessionFactory);
    }
    @Bean
    public UserService userService(UserDao userDaoImpl){
        return new UserService(userDaoImpl);
    }
}
