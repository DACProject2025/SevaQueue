package com.sevaqueue.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long id;
	
	@ManyToMany
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;
	
	@ManyToMany
	@JoinColumn(name = "token_id", nullable = false, updatable = false)
	private Token token;
	
	@Column(name = "message", nullable = false, length = 250)
	private String message;
		
	@Column(name = "sent_time", nullable = false, insertable = false, updatable = false)
	private LocalDateTime sentTime;
	
	@Column(name = "read_status", nullable = false)
	private ReadStatus status;
	
}
