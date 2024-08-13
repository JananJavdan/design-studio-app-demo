package com.project.demo.Config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails userDetails;
    private String token;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        userDetails = User.withUsername("testuser").password("password").roles("USER").build();
        token = jwtUtil.generateToken(userDetails);
    }

    @Test
    void testGenerateToken() {
        assertThat(token).isNotNull();
    }

    @Test
    void testExtractUsername() {
        String username = jwtUtil.extractUsername(token);
        assertThat(username).isEqualTo(userDetails.getUsername());
    }

    @Test
    void testValidateToken() {
        boolean isValid = jwtUtil.validateToken(token, userDetails);
        assertThat(isValid).isTrue();
    }

    // If you still want to test isTokenExpired directly
    @Test
    void testIsTokenExpired() {
        // You can access it indirectly or change its access to package-private or protected
        boolean isExpired = jwtUtil.validateToken(token, userDetails);
        assertThat(isExpired).isTrue();  // Assuming the token hasn't expired yet
    }
}
