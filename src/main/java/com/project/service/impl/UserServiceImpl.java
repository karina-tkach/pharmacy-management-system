package com.project.service.impl;

import com.project.entity.user.Role;
import com.project.entity.user.User;
import com.project.entity.user.UserValidator;
import com.project.exception.UserValidationException;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User addUser(User user) {
        try {
            userValidator.validate(user);

            User existsUser = getUserByEmail(user.getEmail());
            if (existsUser != null) {
                throw new UserValidationException(
                        "User with email '" + existsUser.getEmail() + "' already exists"
                );
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            Optional<User> retrievedUser = userRepository.findByEmail(user.getEmail());
            return retrievedUser.orElse(null);
        } catch (UserValidationException | DataAccessException exception) {
            return null;
        }
    }

    @Override
    public User getUserById(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            return user.orElse(null);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            return user.orElse(null);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    @Override
    public boolean deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (DataAccessException exception) {
            return false;
        }
    }

    @Override
    @Transactional
    public User updateUserById(User user, Long id) {
        User userToUpdate = getUserById(id);
        try {
            userValidator.validateUsersForUpdate(userToUpdate, user);

            userRepository.updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), id);

            return getUserById(id);
        } catch (UserValidationException | DataAccessException exception) {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers(Role role) {
        try {
            return userRepository.findAllByRole(role);
        } catch (DataAccessException exception) {
            return Collections.emptyList();
        }
    }
}
