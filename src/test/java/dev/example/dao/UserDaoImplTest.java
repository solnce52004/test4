package dev.example.dao;

import dev.conf.TestConfig;
import dev.example.dto.UserFullDTO;
import dev.example.entities.Address;
import dev.example.entities.Role;
import dev.example.entities.User;
import dev.example.entities.embeddable.Status;
import dev.example.entities.enams.EnumRole;
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
        Status isActualRole = new Status(true);

        role.setTitle(EnumRole.ADMIN);
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
//                        .atZone(ZoneId.of(Constants.CURRENT_TIMEZONE))
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
    void createWithAudit() {
        final Long id = userDao.createWithAudit(user);
        final User NewUserById = userDao.findById(id);

        System.out.println(NewUserById);
        System.out.println(id);

        assertThat(id).isPositive();
        assertThat(NewUserById).isNotNull();
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
        role.setTitle(EnumRole.ADMIN);
        final List<UserFullDTO> users = userDao.findAllByRole(role);
        users.forEach(System.out::println);
        assertThat(users).isNotEmpty();
    }

    //TODO: Could not resolve PropertyAccess for this on class dev.example.dto.UserDTO
    @Test
    void findById() {
        final User user = userDao.findById(5120L);
        System.out.println(user);
        assertThat(user).isNotNull();
    }

    @Test
    void update(){
        final Long createdId = userDao.create(user);
        assertThat(createdId).isNotNull();

        System.out.println(user);
        final String username = "ttt";
        user.setUsername(username);

        userDao.update(user);
        final User userUpdated = userDao.findById(createdId);
        System.out.println(userUpdated);

        assertThat(userUpdated.getUsername()).isEqualTo(username);
    }

    @Test
    void updateById(){
        final User userById = userDao.findById(5065L);
        System.out.println(userById);

        final String username = "123456789";
        userById.setUsername(username);

        userDao.update(userById);

        final User userUpdated = userDao.findById(userById.getId());
        System.out.println(userUpdated);
        System.out.println(userUpdated.getUpdatedAt());

        assertThat(userUpdated.getUsername()).isEqualTo(username);
    }
}