package com.sevaqueue.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OfficeRequestDto {

	private String OfficeName;
	private String address;
	private String city;
	private String state;
	private LocalTime openTime;
	private LocalTime closeTime; 
	
}
