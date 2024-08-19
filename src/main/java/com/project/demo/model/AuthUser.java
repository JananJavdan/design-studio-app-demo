package com.project.demo.model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends User{
    private String email;
    private String password;
    public AuthUser() {
    }

    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
