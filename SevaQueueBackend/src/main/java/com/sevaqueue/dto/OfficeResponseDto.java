package com.sevaqueue.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfficeResponseDto {

	private Long officeId;
	private String officeName;
	private String city;
	private LocalTime openTime;
	private LocalTime closeTime;
	
}
