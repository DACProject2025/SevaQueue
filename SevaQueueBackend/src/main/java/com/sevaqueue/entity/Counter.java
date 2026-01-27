package com.sevaqueue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "counters")
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counter_id")
    private Long id;

    @Column(name = "counter_number", nullable = false)
    private int counterNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CounterStatus status = CounterStatus.OPEN;   // OPEN / CLOSED


    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private OfficeService service;
    
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private User staff;

}
