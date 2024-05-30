package com.project.service;

import com.project.entity.user.Role;
import com.project.entity.user.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    User getUserById(Long id);

    User getUserByEmail(String email);

    boolean deleteUserById(Long id);

    User updateUserById(User user, Long id);

    List<User> getAllUsers(Role role);
}
