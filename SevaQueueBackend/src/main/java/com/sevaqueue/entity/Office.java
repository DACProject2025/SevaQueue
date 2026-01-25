package com.sevaqueue.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "office_id")
    private Long officeId;
	
	@Column(name = "office_name" , nullable = false, length = 100)
	private String name;
	
	@Column(name = "address", nullable = false, length = 150)
	private String address;
	
	@Column(name = "city", nullable = false, length = 40)
	private String city;
	
	@Column(name = "state", nullable = false, length = 40)
	private String state;
	
	@Column(name = "opening_time", nullable = false)
	private LocalTime openTime;
	
	@Column(name = "closing_time", nullable = false)
	private LocalTime closeTime;
	
}
