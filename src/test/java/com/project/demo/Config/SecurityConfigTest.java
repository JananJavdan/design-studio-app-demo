package com.project.demo.Config;

import com.project.demo.Services.CustomUserDetailsService;
import com.project.demo.Security.CustomUserDetails;
import com.project.demo.Security.JwtUtil;
import com.project.demo.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        // Gebruik een gecodeerd wachtwoord
        String encodedPassword = new BCryptPasswordEncoder().encode("password");
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        customer.setPassword(encodedPassword);

        UserDetails userDetails = new CustomUserDetails(customer);

        Mockito.when(customUserDetailsService.loadUserByUsername("test@example.com"))
                .thenReturn(userDetails);
    }


    @Test
    public void testLoginEndpoint() throws Exception {
        String loginJson = "{\"email\":\"test@example.com\",\"password\":\"password\"}";

        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(loginJson))
                .andExpect(status().isOk());
    }
}
