package com.example.thtbp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id")
    private ServiceEntity service;

    @Column(nullable = false, unique = true)
    private String invoice_number;

    @Column(nullable = false)
    private String transaction_type; // TOPUP or PAYMENT

    @Column(nullable = false)
    private Double total_amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime created_on = LocalDateTime.now();
}
