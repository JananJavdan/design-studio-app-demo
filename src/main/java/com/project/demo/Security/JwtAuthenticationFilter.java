package com.project.demo.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.model.AuthUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super("/login"); // Set the login URL
        setAuthenticationManager(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthUser creds = new ObjectMapper().readValue(request.getInputStream(), AuthUser.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), Collections.emptyList());

            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException("Invalid login request", e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        UserDetails user = (UserDetails) authResult.getPrincipal();

        // Genereer JWT token
        String token = jwtUtil.generateToken(user);

        // Zet de token in de response header (optioneel)
        response.setHeader("Authorization", "Bearer " + token);

        // Of retourneer de token in de response body
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }

}
