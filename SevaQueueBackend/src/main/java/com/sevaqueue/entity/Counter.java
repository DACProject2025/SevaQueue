package com.sevaqueue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="counter")
public class Counter {
	
	@Id
	@Column(name = "counter_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "counter_name", nullable = false, length = 50)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "office_id", nullable = false)
	private Office oid;
	
	@ManyToOne
	@JoinColumn(name = "service_id", nullable = false)
	private Service sid;
}
