package com.example.brs.modal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "buses")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String busNumber;


    private String driverName;


    private String route;


    private int capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
}
