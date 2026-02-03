package com.arqetype.growli.entity;

import com.arqetype.growli.util.EntityFieldParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("Password1!");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
    }

    @Test
    void testEntityFieldValues() {
        Map<String, Object> fieldValues = EntityFieldParser.parseEntityFields(user);

        assertEquals(1L, fieldValues.get("id"));
        assertEquals("testuser", fieldValues.get("username"));
        assertEquals("test@example.com", fieldValues.get("email"));
        assertEquals("Password1!", fieldValues.get("password"));
        assertTrue((Boolean) fieldValues.get("enabled"));
        assertTrue((Boolean) fieldValues.get("accountNonExpired"));
        assertTrue((Boolean) fieldValues.get("accountNonLocked"));
        assertTrue((Boolean) fieldValues.get("credentialsNonExpired"));
    }
}
