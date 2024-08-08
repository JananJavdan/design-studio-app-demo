package com.project.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Design {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long designID;
    private Long userID;
    private String imagePath;

    @ManyToOne
    private User user;


}
