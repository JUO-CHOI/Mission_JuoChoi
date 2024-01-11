package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    private String text;
    private String password;
}
