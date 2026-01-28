package com.sevaqueue.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponseDto {

	private Long tokenId;
	private int tokenNumber;
	private String status;
	private LocalDateTime createdAt;
	
}
