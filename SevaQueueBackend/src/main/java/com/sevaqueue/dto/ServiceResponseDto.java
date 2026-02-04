package com.sevaqueue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponseDto {

    private Long serviceId;
    private String serviceName;
    private String description;
    private int avgServiceTime;
    private int maxTokensPerDay;
    private boolean active;

}
