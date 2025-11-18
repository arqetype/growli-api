package com.arqetype.growli.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arqetype.growli.model.User;
import com.arqetype.growli.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers()
                .map(users -> new ResponseEntity<>(users, HttpStatus.OK))
                .getOrElseGet(ex -> {
                    logger.severe(ex.getMessage());
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                });
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .getOrElseGet(ex -> {
                    logger.severe(ex.getMessage());
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return userService.createUser(user)
                .map(createdUser -> new ResponseEntity<>(createdUser, HttpStatus.CREATED))
                .getOrElseGet(ex -> {
                    logger.info(ex.getMessage());
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                });
    }
}
