package com.sevaqueue.entity;

import java.time.LocalDateTime;

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

@Entity
@Table(name="call_log")
public class CallLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "token_id", nullable = false, updatable = false)
	private Token tid;
	
	@ManyToOne
	@JoinColumn(name = "staff_id", nullable = false, updatable = false)
	private Staff sid;
	
	@ManyToOne
	@JoinColumn(name = "counter_id", nullable = false, updatable = false)
	private Counter cid;
		
	@Enumerated(EnumType.STRING)
	@Column(name = "status_after", nullable = false)
	private StatusAfter status;
	
	@Column(name = "timestamp", nullable = false, insertable = false, updatable = false)
	private LocalDateTime timestamp;
	
	
}
