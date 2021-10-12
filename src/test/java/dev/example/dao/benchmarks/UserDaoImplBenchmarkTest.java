package dev.example.dao.benchmarks;


import dev.conf.AppConfig;
import dev.conf.TestConfig;
import dev.example.dao.UserDaoImpl;
import dev.example.entities.Address;
import dev.example.entities.Role;
import dev.example.entities.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjdk.jmh.annotations.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {TestConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class UserDaoImplBenchmarkTest extends AbstractBenchmark {

    private static UserDaoImpl userDao;

    private final User user = new User();
    private final Role role = new Role();
    private final List<Role> roles = new ArrayList<>();
    private final Address address = new Address();


    @Setup
    public void setup() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.register(TestConfig.class);
        context.refresh();

        userDao = context.getBean(UserDaoImpl.class);


        role.setTitle("quest");
        role.setLevel(2);
        roles.add(role);

        address.setCity("Moscow");
        address.setStreet("Nah");
        address.setNumBuilding(123);
    }

    @TearDown
    public void tearDown() {
        userDao.remove(user);
    }


    @Benchmark
    public void createUser(UserNameState state) {

        user.setUsername(state.userName);
        user.setRoles(roles);
        user.setAddresses(address);

        userDao.create(user);
    }
}
