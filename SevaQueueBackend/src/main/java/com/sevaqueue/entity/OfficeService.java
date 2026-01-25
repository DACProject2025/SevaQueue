package com.sevaqueue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="service")
public class OfficeService {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="service_id")
	private Long serviceId;
	
	@Column(name="service_name", nullable = false, length = 75)
	private String name;
	
	 @Column(name = "avg_service_time", nullable = false)
	 private int avgServiceTime;   // in minutes
	
	@ManyToOne
	@JoinColumn(name="office_id", nullable=false)
	private Office office;

}
