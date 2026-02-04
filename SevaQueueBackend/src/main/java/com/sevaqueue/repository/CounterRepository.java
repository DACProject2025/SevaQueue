package com.sevaqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sevaqueue.entity.CounterStatus;
import com.sevaqueue.entity.Counter;

@Repository

public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findByServiceServiceId(Long serviceId);

    List<Counter> findByServiceServiceIdAndStatus(Long serviceId, CounterStatus status);

    List<Counter> findByStaff_Id(Long staffId);

    @Query("""
                SELECT COUNT(c)
                FROM Counter c
                WHERE c.service.office.officeId = :officeId
            """)
    long countCountersByOfficeId(@Param("officeId") Long officeId);
}
