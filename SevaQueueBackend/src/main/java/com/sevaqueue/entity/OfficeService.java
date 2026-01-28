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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="services")
public class OfficeService {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="service_id")
	private Long serviceId;
	
	@Column(name="service_name", nullable = false, length = 75)
	private String serviceName;
	
	@Column(name = "avg_service_time", nullable = false)
	private Integer avgServiceTime;   // in minutes
	
	@ManyToOne
	@JoinColumn(name="office_id", nullable=false)
	private Office office;
	
	@Column(nullable = false)
	private boolean active = true;
	
	@Column(nullable = false)
	private int maxTokensPerDay;

}
