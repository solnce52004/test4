package dev.conf;

import dev.example.dao.AddressDaoImpl;
import dev.example.dao.RoleDaoImpl;
import dev.example.dao.interfaces.UserDao;
import dev.example.dao.UserDaoImpl;
import dev.example.services.UserService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
@Import(TestHibernateConfig.class)
public class TestConfig {

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
