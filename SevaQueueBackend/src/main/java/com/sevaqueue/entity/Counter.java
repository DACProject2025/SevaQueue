package com.sevaqueue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
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
	
}
