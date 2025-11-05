package com.example.thtbp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "banners")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long banner_id;

    @Column(nullable = false)
    private String banner_name;

    @Column(nullable = false)
    private String banner_image;

    @Column(nullable = false)
    private String description;
}
