package com.sevaqueue.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "tokens")
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_id")
	private Long id;
	
	@Column(name = "token_number", nullable = false)
	private int tokenNumber;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private TokenStatus status = TokenStatus.WAITING;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	
	@ManyToOne
	@JoinColumn(name = "service_id", nullable = false)
	private OfficeService service;

}
