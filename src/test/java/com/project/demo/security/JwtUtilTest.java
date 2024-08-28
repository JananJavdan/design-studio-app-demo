package com.project.demo.security;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "jwt.secret=test_secret")
public class JwtUtilTest {


    @Autowired
    private JwtUtil jwtUtil;



    @Test
    public void testGenerateToken() {
        UserDetails userDetails = User.withUsername("test@example.com").password("password").authorities("ROLE_USER").build();
        String token = jwtUtil.generateToken(userDetails);
        String username = jwtUtil.extractUsername(token);

        assertTrue(username.equals(userDetails.getUsername()));
    }

    @Test
    public void testValidateToken_ValidToken() {
        UserDetails userDetails = User.withUsername("test@example.com").password("password").authorities("ROLE_USER").build();
        String token = jwtUtil.generateToken(userDetails);

        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    public void testValidateToken_InvalidToken() {
        JwtUtil mockJwtUtil = Mockito.mock(JwtUtil.class);
        UserDetails userDetails = User.withUsername("test@example.com").password("password").authorities("ROLE_USER").build();

        Mockito.when(mockJwtUtil.validateToken(Mockito.anyString(), Mockito.eq(userDetails)))
                .thenReturn(false);

        assertFalse(mockJwtUtil.validateToken("invalidToken", userDetails));
    }

}
