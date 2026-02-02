package com.sevaqueue.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceRequestDto {

	private String serviceName;
	private int avgServiceTime;
	private int maxTokensPerDay;
	
}
