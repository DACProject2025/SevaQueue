package com.sevaqueue.entity;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
	@JsonFormat(pattern = "HH:mm")
	private LocalTime openTime;

	@Column(name = "closing_time", nullable = false)
	@JsonFormat(pattern = "HH:mm")
	private LocalTime closeTime;
	
}
