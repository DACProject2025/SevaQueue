package com.sevaqueue.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "token")
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_id")
	private Long id;
	
	@Column(name = "token_number", nullable = false)
	private int tno;
	
	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	@Column(name = "time_slot", nullable = false)
	private LocalTime timeSlot;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private TokenStatus status = TokenStatus.WAITING;
	
	@Column(name = "issue_time", nullable = false, insertable = false, updatable = false)
	private LocalDateTime issueTime;
	
	@OneToMany
	@JoinColumn(name = "user_id", nullable = false)
	private User uid;
	
	@ManyToOne
	@JoinColumn(name = "office_id", nullable = false)
	private Office oid;
	
	@ManyToOne
	@JoinColumn(name = "service_id", nullable = false)
	private Service sid;
}
