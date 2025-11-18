package com.arqetype.growli.controller;

import java.util.List;
import java.util.logging.Logger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arqetype.growli.model.User;
import com.arqetype.growli.service.UserService;

@RestController
@Tag(name = "Users", description = "Operations related to users")
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers()
                .map(users -> new ResponseEntity<>(users, HttpStatus.OK))
                .getOrElseGet(ex -> {
                    logger.severe(ex.getMessage());
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                });
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Retrieve a user by id")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true) @PathVariable Long id
    ) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .getOrElseGet(ex -> {
                    logger.severe(ex.getMessage());
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user")
    public ResponseEntity<User> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to be created", required = true) @RequestBody User user
    ) {
        return userService.createUser(user)
                .map(createdUser -> new ResponseEntity<>(createdUser, HttpStatus.CREATED))
                .getOrElseGet(ex -> {
                    logger.info(ex.getMessage());
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                });
    }
}
