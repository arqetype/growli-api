package com.arqetype.growli.controller;

import com.arqetype.growli.dto.LoginRequest;
import com.arqetype.growli.entity.User;
import com.arqetype.growli.spring.security.JwtUtil;
import com.arqetype.growli.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "User authentication and authorization")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtUtil jwtUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign in User", description = "Authenticate user and return JWT token")
    public ResponseEntity<String> authenticateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User credentials", required = true) @RequestBody LoginRequest request
    ) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.getIdentifier(),
                request.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(jwtUtil.generateToken(userDetails.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Sign up User", description = "Create a new user")
    public ResponseEntity<User> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to be created", required = true) @Valid @RequestBody User user
    ) {
        return userService.createUser(user)
                .map(createdUser -> new ResponseEntity<>(createdUser, HttpStatus.CREATED))
                .getOrElseGet(_ -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }
}
