package com.sevaqueue.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeRequestDto {

	private String OfficeName;
	private String address;
	private String city;
	private String state;
	private LocalTime openTime;
	private LocalTime closeTime; 
	
}
