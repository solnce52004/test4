package dev.example.dao;

import dev.conf.TestConfig;
import dev.example.dto.UserFullDTO;
import dev.example.entities.Address;
import dev.example.entities.Role;
import dev.example.entities.User;
import dev.example.entities.embeddable.Status;
import io.sniffy.sql.SqlExpectation;
import io.sniffy.test.Count;
import io.sniffy.test.spring.SniffySpringTestListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {TestConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@TestExecutionListeners(
        value = SniffySpringTestListener.class,
        mergeMode = MERGE_WITH_DEFAULTS
)
class UserDaoImplTest {

    @Autowired
    UserDaoImpl userDao;

    private final User user = new User();
    private final Role role = new Role();
    private final List<Role> roles = new ArrayList<>();
    private final Address address = new Address();

    @BeforeEach
    void before() {
        Status isActualUser = new Status(true);
        Status isActualRole = new Status(false);

        role.setTitle("quest");
        role.setLevel(4);
        role.setIsActual(isActualRole);
        roles.add(role);

        address.setCity("Moscow");
        address.setStreet("Nah");
        address.setNumBuilding(123);
        address.setVerifiedAt(
//                Timestamp.from(
                LocalDateTime.now()
                        .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
//                        .atZone(ZoneId.of(BaseEntity.CURRENT_TIMEZONE))
//                        .toInstant()
//                )
        );

        user.setUsername("newtest");
        user.setRoles(roles);
        user.setAddresses(address);
        user.setIsActual(isActualUser);
    }

//    @AfterEach
//    void after(){
//        userDao.remove(user);
//    }

    @Test
    void create() {
        final Long actual = userDao.create(user);
        System.out.println(userDao.findById(actual));
        assertThat(actual).isNotNull();
    }

    @Test
    @SqlExpectation(count = @Count(max = 5))
    void findAll() {
        final List<User> users = userDao.findAll();
        System.out.println(users);
        assertThat(users).isNotEmpty();
    }

    @Test
    void findAllByRole() {
        final Role role = new Role();
        role.setTitle("admin");
        final List<UserFullDTO> users = userDao.findAllByRole(role);
        users.forEach(System.out::println);
        assertThat(users).isNotEmpty();
    }

    //TODO: Could not resolve PropertyAccess for this on class dev.example.dto.UserDTO
    @Test
    void findById() {
        final User user = userDao.findById(5018L);
        System.out.println(user);
        assertThat(user).isNotNull();
    }
}