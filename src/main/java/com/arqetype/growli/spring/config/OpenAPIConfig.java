package com.arqetype.growli.spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI defineOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development server");

        Contact contact = new Contact();
        contact.setName("Clément OMNÈS");
        contact.setEmail("contact@clementomnes.dev");

        Info information = new Info()
                .title("Growli API")
                .version("1.0.0")
                .description("API for managing users in the Growli application.")
                .contact(contact);
        return new OpenAPI().info(information).addServersItem(server);
    }
}
