package com.sevaqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sevaqueue.entity.Counter;
import com.sevaqueue.entity.CounterStatus;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findByServiceId(Long serviceId);

    List<Counter> findByServiceIdAndStatus(Long serviceId, CounterStatus status);
}
