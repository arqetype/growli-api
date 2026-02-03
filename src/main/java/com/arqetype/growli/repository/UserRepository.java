package com.arqetype.growli.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arqetype.growli.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:identifier) OR LOWER(u.email) = LOWER(:identifier)")
    Optional<User> findByUsernameOrEmailIgnoreCase(@Param("identifier") String identifier);

}
