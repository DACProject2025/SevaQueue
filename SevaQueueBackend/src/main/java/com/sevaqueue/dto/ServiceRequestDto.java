package com.sevaqueue.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRequestDto {

	private String serviceName;
	private int avgServiceTime;
	private int maxTokenPerDay;
	
}
