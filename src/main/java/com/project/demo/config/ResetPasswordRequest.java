package com.project.demo.config;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String newPassword;
    private String token;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
