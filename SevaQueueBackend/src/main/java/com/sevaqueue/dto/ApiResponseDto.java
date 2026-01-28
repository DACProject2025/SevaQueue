package com.sevaqueue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseDto {

	private String message;
	private boolean success;
	
}
