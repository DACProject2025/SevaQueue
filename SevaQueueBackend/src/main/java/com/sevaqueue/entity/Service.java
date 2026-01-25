package com.sevaqueue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
<<<<<<< HEAD
import lombok.Getter;
=======
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
>>>>>>> 48004d787c36e980746bf36827f47403342b9434
import lombok.Setter;

@Entity
@Getter
@Setter
<<<<<<< HEAD
=======
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> 48004d787c36e980746bf36827f47403342b9434
@Table(name="service")
public class Service {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="service_id")
	private Long id;
	
	@Column(name="service_name", nullable = false, length = 75)
	private String name;
	
	 @Column(name = "avg_service_time", nullable = false)
	 private int avgServiceTime;   // in minutes
	
	@ManyToOne
	@JoinColumn(name="office_id", nullable=false)
	private Office office;

}
