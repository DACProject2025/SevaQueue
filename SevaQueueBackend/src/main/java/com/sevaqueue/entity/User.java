package com.sevaqueue.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "uname", nullable = false, length = 50)
	private String name;
	
	@Column(name = "mobile", nullable = false, length = 15)
	private String mobile;
	
	@Column(name = "email", nullable = false, length = 60)
	private String email;

	@Column(name = "password", nullable = false, length = 260)
	private String password;
	
	@Column(name = "created_on", nullable = false, insertable = false, updatable = false)
	private LocalDate createdOn;
}
