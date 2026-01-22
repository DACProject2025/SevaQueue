package com.sevaqueue.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="request_logs")
public class RequestLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
	private Long id;
	
	@Column(name = "endpoint", nullable = false, updatable = false, length = 100)
	private String endpoint;
	
	@Column(name = "method", nullable = false, updatable = false, length = 10)
	private String method;
	
	@Column(name = "status_code", nullable = false, updatable = false)
	private int statusCode;
	
	@CreationTimestamp
	@Column(name = "timestamp", nullable = false, insertable = false, updatable = false)
	private LocalDateTime timestamp;
		
}
