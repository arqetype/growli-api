package com.arqetype.growli.entity;

import com.arqetype.growli.util.EntityFieldParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileTest {

    private UserProfile userProfile;

    @BeforeEach
    void setUp() {
        userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setAvatar(new byte[]{1, 2, 3});
        userProfile.setCreatedAt(1633024800000L);
        userProfile.setUpdatedAt(1633111200000L);
    }

    @Test
    void testEntityFieldValues() {
        Map<String, Object> fieldValues = EntityFieldParser.parseEntityFields(userProfile);

        assertEquals(1L, fieldValues.get("id"));
        assertEquals("John", fieldValues.get("firstName"));
        assertEquals("Doe", fieldValues.get("lastName"));
        assertArrayEquals(new byte[]{1, 2, 3}, (byte[]) fieldValues.get("avatar"));
        assertEquals(1633024800000L, fieldValues.get("createdAt"));
        assertEquals(1633111200000L, fieldValues.get("updatedAt"));
    }
}
