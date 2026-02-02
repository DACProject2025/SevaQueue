package com.sevaqueue.service;

import java.util.List;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.dto.CounterRequestDto;
import com.sevaqueue.dto.CounterResponseDto;
import com.sevaqueue.entity.Counter;
import com.sevaqueue.entity.CounterStatus;

public interface CounterService {

	CounterResponseDto assignCounter(Long serviceId, Long staffId, Integer counterNumber);

	List<CounterResponseDto> getCountersByService(Long serviceId);

	ApiResponseDto updateCounterStatus(Long counterId, CounterStatus status);

	Counter createCounter(CounterRequestDto dto);

}
