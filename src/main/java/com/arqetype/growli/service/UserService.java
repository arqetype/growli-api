package com.arqetype.growli.service;

import java.util.List;

import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.arqetype.growli.entity.User;
import com.arqetype.growli.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Get all users
     * @return List of users or an exception if no users are found
     */
    public Either<IllegalArgumentException, List<User>> getAllUsers() {
        return userRepository.findAll().isEmpty()
                ? Either.left(new IllegalArgumentException("No users found"))
                : Either.right(userRepository.findAll());
    }

    /**
     * Get user by id
     * @param id User id
     * @return User or an exception if no user is found with the given id
     */
    public Either<IllegalArgumentException, User > getUserById(Long id) {
        return userRepository.findById(id)
                .map(Either::<IllegalArgumentException, User>right)
                .orElseGet(() -> Either.left(new IllegalArgumentException("User not found with id: " + id)));
    }

    /**
     * Create a new user
     * @param user User to be created
     * @return User or an exception if the username or email is already taken
     */
    public Either<IllegalArgumentException, User> createUser(@NotNull User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return Either.left(new IllegalArgumentException("Username is already taken!"));
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return Either.left(new IllegalArgumentException("Email is already in use!"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Either.right(userRepository.save(user));
    }
}
