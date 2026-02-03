package com.sevaqueue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CounterResponseDto {

    private Long counterId;
    private int counterNumber;

    private Long serviceId;
    private String serviceName;

    private Long officeId;
    private String officeName;

    private Long staffId;
    private String staffName;

    private String status;
}
