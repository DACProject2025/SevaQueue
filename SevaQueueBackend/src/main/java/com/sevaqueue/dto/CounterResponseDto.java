package com.sevaqueue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CounterResponseDto {

	private Long counterId;
	private int counterNumber;
	private String status;
	
}
