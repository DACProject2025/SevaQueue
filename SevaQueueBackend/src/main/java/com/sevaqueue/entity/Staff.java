package com.sevaqueue.entity;

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
@Table(name = "staff")
public class Staff {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "uname", nullable = false, length = 50)
	private String name;
	
	@Column(name = "mobile", nullable = false, length = 15)
	private String mobile;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@OneToMany
	@JoinColumn(name = "counter_id", nullable = false)
	private Counter cid;
	
	@ManyToOne
	@JoinColumn(name = "call_log_id", nullable = false)
	private CallLog clid;
	
}
