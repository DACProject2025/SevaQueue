package com.sevaqueue.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CounterRequestDto {

	private int counterNumber;
	private Long serviceId;
	private Long staffId;
	
}
