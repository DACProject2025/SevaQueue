package com.sevaqueue.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "counter")
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counter_id")
    private Long id;

    @Column(name = "counter_number", nullable = false)
    private int number;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CounterStatus status;   // OPEN / CLOSED


    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    // getters & setters
}
