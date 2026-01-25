package com.sevaqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sevaqueue.entity.Counter;
import com.sevaqueue.entity.CounterStatus;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findByServiceId(Long serviceId);

    List<Counter> findByServiceIdAndStatus(Long serviceId, CounterStatus status);
    
    @Query("""
            SELECT COUNT(c)
            FROM Counter c
            WHERE c.service.office.id = :officeId
        """)
        long countCountersByOfficeId(Long officeId);
}
