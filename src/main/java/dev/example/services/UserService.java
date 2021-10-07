package dev.example.services;

import dev.example.dao.interfaces.UserDao;
import dev.example.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void create(User user){
        userDao.create(user);
    }
}
