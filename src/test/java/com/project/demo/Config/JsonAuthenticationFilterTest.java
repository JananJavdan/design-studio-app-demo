package com.project.demo.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JsonAuthenticationFilterTest {

    private JsonAuthenticationFilter jsonAuthenticationFilter;
    private AuthenticationManager authenticationManager;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        jsonAuthenticationFilter = new JsonAuthenticationFilter(authenticationManager);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void testAttemptAuthenticationSuccess() throws IOException {
        // Mock request input
        when(request.getInputStream()).thenReturn(new MockServletInputStream(
                "{\"email\": \"user@example.com\", \"password\": \"password\"}".getBytes()));

        // Mock the authentication process
        Authentication authentication = new UsernamePasswordAuthenticationToken("user@example.com", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        Authentication result = jsonAuthenticationFilter.attemptAuthentication(request, response);

        // Assert that the authentication was successful
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("user@example.com");
    }

    @Test
    void testAttemptAuthenticationFailure() throws IOException {
        // Mock request input
        when(request.getInputStream()).thenReturn(new MockServletInputStream(
                "{\"email\": \"user@example.com\", \"password\": \"wrongpassword\"}".getBytes()));

        // Mock the authentication process to throw a specific exception
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        try {
            jsonAuthenticationFilter.attemptAuthentication(request, response);
        } catch (RuntimeException e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
        }
    }

    @Test
    void testSuccessfulAuthentication() throws IOException, ServletException {
        Authentication authResult = mock(Authentication.class);
        when(authResult.getName()).thenReturn("user@example.com");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        jsonAuthenticationFilter.successfulAuthentication(request, response, filterChain, authResult);

        // Verify that the response was set correctly
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(writer).write("Login successful");
    }

    @Test
    void testUnsuccessfulAuthentication() throws IOException, ServletException {
        AuthenticationException failed = new BadCredentialsException("Bad credentials");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        jsonAuthenticationFilter.unsuccessfulAuthentication(request, response, failed);

        // Verify that the response was set correctly
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(writer).write("Login failed");
    }

    // MockServletInputStream Implementation
    private static class MockServletInputStream extends jakarta.servlet.ServletInputStream {
        private final InputStream delegate;

        public MockServletInputStream(byte[] data) {
            this.delegate = new ByteArrayInputStream(data);
        }

        @Override
        public int read() throws IOException {
            return delegate.read();
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
            throw new UnsupportedOperationException();
        }
    }
}
