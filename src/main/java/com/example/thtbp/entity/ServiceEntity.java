package com.example.thtbp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long service_id;

    @Column(nullable = false, unique = true)
    private String service_code;

    @Column(nullable = false)
    private String service_name;

    @Column(nullable = false)
    private String service_icon;

    @Column(nullable = false)
    private Double service_tariff;
}
