package com.sevaqueue.dto;

import com.sevaqueue.entity.TokenStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueueStatusDto {
	private String currentServingTokenNumber;
	private int waitingBeforeUser;
	private String userTokenNumber;
	private TokenStatus status;
	private boolean isUserTurn;
	private int avgServiceTime; // in minutes
}
