package com.sevaqueue.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OfficeResponseDto {

	private Long officeId;
	private String officeName;
	private String city;
	private LocalTime openTime;
	private LocalTime closeTime;
	
}
