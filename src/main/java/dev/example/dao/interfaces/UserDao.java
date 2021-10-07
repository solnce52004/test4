package dev.example.dao.interfaces;

import dev.example.dao.dto.UserDTO;
import dev.example.dao.dto.UserFullDTO;
import dev.example.entities.Role;
import dev.example.entities.User;

import java.util.List;

public interface UserDao extends CRUDDAO<User, Long> {
    Long create(User user);

    void remove(User user);

    UserDTO findById(Long id);

    List<User> findAll();

    List<UserFullDTO> findAllByRole(Role role);
}
