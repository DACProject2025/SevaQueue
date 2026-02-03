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
	private String address;
	private String state;
	private LocalTime openTime;
	private LocalTime closeTime;
	@com.fasterxml.jackson.annotation.JsonProperty("active")
	private boolean active;

	// Constructor for findActiveOffices() query
	public OfficeResponseDto(Long officeId, String officeName, String city, LocalTime openTime, LocalTime closeTime,
			boolean active) {
		this.officeId = officeId;
		this.officeName = officeName;
		this.city = city;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.active = active;
	}

}
