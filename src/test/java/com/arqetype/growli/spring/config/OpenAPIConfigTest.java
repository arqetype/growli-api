package com.arqetype.growli.spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenAPIConfigTest {

    @Test
    void testDefineOpenAPI() {
        OpenAPIConfig openAPIConfig = new OpenAPIConfig();
        OpenAPI openAPI = openAPIConfig.defineOpenAPI();

        assertNotNull(openAPI);

        assertNotNull(openAPI.getServers());
        assertEquals(1, openAPI.getServers().size());
        Server server = openAPI.getServers().get(0);
        assertEquals("http://localhost:8080", server.getUrl());
        assertEquals("Development server", server.getDescription());

        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("Growli API", info.getTitle());
        assertEquals("1.0.0", info.getVersion());
        assertEquals("API for managing users in the Growli application.", info.getDescription());

        Contact contact = info.getContact();
        assertNotNull(contact);
        assertEquals("Clément OMNÈS", contact.getName());
        assertEquals("contact@clementomnes.dev", contact.getEmail());
    }
}
