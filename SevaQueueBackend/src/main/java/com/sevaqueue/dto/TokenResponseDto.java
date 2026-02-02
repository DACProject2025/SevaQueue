package com.sevaqueue.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {

	private Long tokenId;
	private int tokenNumber;
	private String status;
	private LocalDateTime createdAt;
	private String serviceName;
	private String officeName;
	
}
