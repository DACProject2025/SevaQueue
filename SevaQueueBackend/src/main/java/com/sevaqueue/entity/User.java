package com.sevaqueue.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "uname", nullable = false, length = 100)
	private String name;
	
	@Column(name = "email", unique = true, nullable = false, length = 60)
	private String email;

	@Column(name = "password", nullable = false, length = 255)
	private String password;
	
	@Column(name = "mobile", nullable = false, length = 15)
	private String mobile;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDate createdAt;
}
