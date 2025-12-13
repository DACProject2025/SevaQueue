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
@Table(name="service")
public class Service {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="service_id")
	private Long id;
	
	@Column(name="service_name", nullable = false, length = 75)
	private String name;
	
	@Column(name="description", nullable = false, length = 500)
	private String desc;
	
	@ManyToOne
	@JoinColumn(name="office_id", nullable=false)
	private Office office;

}
