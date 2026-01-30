package com.sevaqueue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CounterResponseDto {

	private Long counterId;
	private int counterNumber;
	private String status;
	
}
