package com.sevaqueue.service;

import com.sevaqueue.entity.Counter;

public interface CounterService {

	Counter assignCounter(Long serviceId, Long staffId, Integer counterNumber);

}
