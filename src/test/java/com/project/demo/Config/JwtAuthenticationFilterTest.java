package com.project.demo.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.model.AuthUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import java.io.ByteArrayInputStream;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        authenticationManager = mock(AuthenticationManager.class);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtUtil);
    }

    @Test
    void testAttemptAuthentication() throws Exception {
        String email = "user@example.com";
        String password = "password";

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Mocking the InputStream to simulate user credentials in a request
        AuthUser authUser = new AuthUser(email, password);

        when(request.getInputStream()).thenReturn(new MockServletInputStream(
                new ObjectMapper().writeValueAsBytes(authUser)));

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList()));

        Authentication result = jwtAuthenticationFilter.attemptAuthentication(request, response);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(email);
    }


    // MockServletInputStream Implementation
    private static class MockServletInputStream extends jakarta.servlet.ServletInputStream {
        private final ByteArrayInputStream inputStream;

        public MockServletInputStream(byte[] content) {
            this.inputStream = new ByteArrayInputStream(content);
        }

        @Override
        public int read() {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(jakarta.servlet.ReadListener readListener) {
            // Not implemented
        }
    }
}
