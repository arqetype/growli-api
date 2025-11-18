package com.arqetype.growli.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arqetype.growli.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String email);

    Boolean existsByEmail(String email);
}
