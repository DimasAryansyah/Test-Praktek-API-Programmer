package com.example.thtbp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "balances")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long balance_id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(nullable = false)
    private Double balance = 0.0;
}
