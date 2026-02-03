package com.arqetype.growli.spring.config;

import com.arqetype.growli.spring.security.AuthEntryPointJwt;
import com.arqetype.growli.spring.security.AuthTokenFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() {
        AuthEntryPointJwt authEntryPointJwt = mock(AuthEntryPointJwt.class);
        securityConfig = new SecurityConfig(authEntryPointJwt);
    }
    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder.matches("password", passwordEncoder.encode("password")));
    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        AuthenticationManager result = securityConfig.authenticationManager(authenticationConfiguration);
        assertNotNull(result);
        assertEquals(authenticationManager, result);
    }

    @Test
    void testSecurityFilterChain() throws Exception {
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        when(httpSecurity.securityMatcher(anyString())).thenReturn(httpSecurity);
        when(httpSecurity.csrf(AbstractHttpConfigurer::disable)).thenReturn(httpSecurity);
        when(httpSecurity.cors(cors -> cors.configurationSource(any()))).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(auth ->
                auth.requestMatchers(securityConfig.PUBLIC_URLS).permitAll().anyRequest().authenticated()))
                .thenReturn(httpSecurity);

        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(httpSecurity);

        assertNotNull(securityFilterChain);
        verify(httpSecurity).csrf(any());
    }



    @Test
    void testAuthenticationJwtTokenFilter() {
        AuthTokenFilter authTokenFilter = securityConfig.authenticationJwtTokenFilter();
        assertNotNull(authTokenFilter);
    }
}
