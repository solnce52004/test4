package dev.example.services;

import dev.conf.TestConfig;
import dev.example.entities.Role;
import dev.example.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {
                TestConfig.class
        },
        loader = AnnotationConfigContextLoader.class
)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void create() {
//
    }
}