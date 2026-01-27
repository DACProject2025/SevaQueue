package com.sevaqueue.service;

import java.util.List;

import com.sevaqueue.entity.Counter;
import com.sevaqueue.entity.CounterStatus;

public interface CounterService {

	Counter assignCounter(Long serviceId, Long staffId, Integer counterNumber);

	List<Counter> getCountersByService(Long serviceId);

	Counter updateCounterStatus(Long counterId, CounterStatus status);

}
