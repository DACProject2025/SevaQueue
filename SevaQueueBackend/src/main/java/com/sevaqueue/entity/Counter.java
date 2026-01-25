package com.sevaqueue.entity;

<<<<<<< HEAD
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
=======
import jakarta.persistence.*;
>>>>>>> 48004d787c36e980746bf36827f47403342b9434
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
<<<<<<< HEAD
@Getter
@Setter
@Table(name="counter")
public class Counter {
	
	@Id
	@Column(name = "counter_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long counterId;
	
	@Column(name = "counter_number", nullable = false)
	private Integer counterNumber;
	
	@ManyToOne
	@JoinColumn(name = "service_id", nullable = false)
	private Service service;
	
	@ManyToOne
	@JoinColumn(name = "staff_id", nullable = false)
	private User staff;
	
=======
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
>>>>>>> 48004d787c36e980746bf36827f47403342b9434
}
