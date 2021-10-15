package dev.example.entities.named_queries;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNamedQueries {
    public static final String FIND_USERS_ORDER_BY_NAME = "User_findUsersOrderByName";
    public static final String FIND_USERS_BY_ROLE_NATIVE = "User_findUsersByRole_Native";
}
