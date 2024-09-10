package com.project.demo.config;

import com.project.demo.security.JwtAuthenticationFilter;
import com.project.demo.security.JwtAuthorizationFilter;
import com.project.demo.security.JwtUtil;
import com.project.demo.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil);
        jwtAuthenticationFilter.setFilterProcessesUrl("/users/login");

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("users/login", "/users/register", "/users/confirm", "/oauth2/**","/login", "/designs/**", "/orders/**", "/uploads/**", "/designs/my-designs", "/designs/create", "/send-email", "/users/forgot-password",  "/users/reset-password").permitAll()
                        //.requestMatchers("/admin/**").hasRole("ADMIN")
                        //.requestMatchers("/users/customers").hasRole("CUSTOMER")
                        //.requestMatchers("/orders/**", "/designs/**").hasAnyRole("CUSTOMER")
                        //.requestMatchers(HttpMethod.POST, "/designs/submit").permitAll()  // Allow POST requests to /designs
                        //.requestMatchers(HttpMethod.GET, "/designs/view").permitAll()

                        .anyRequest().authenticated()



                )

                .requiresChannel(channel -> channel
                        .requestMatchers(r -> "https".equals(r.getHeader("X-Forwarded-Proto"))).requiresSecure()
                )
                //.oauth2Login(oauth2 -> oauth2
                  //      .loginPage("/login")
                    //    .defaultSuccessUrl("/home", true)
                      // .defaultSuccessUrl("/profile", true)
           //  )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
