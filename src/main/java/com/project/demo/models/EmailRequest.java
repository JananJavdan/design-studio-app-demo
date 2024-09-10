package com.project.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrementing ID
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    @NotNull
    private String message;

    @NotNull
    private String name;

    @NotNull
    private String email;

    public EmailRequest(String message, String name, String email) {
        this.message = message;
        this.name = name;
        this.email = email;
    }

    public EmailRequest() {
    }

    @Override
    public String toString() {
        return "EmailRequest{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
