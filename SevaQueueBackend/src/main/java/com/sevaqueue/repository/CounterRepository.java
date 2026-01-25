package com.sevaqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sevaqueue.entity.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {

	List<Counter> findByServiceId(Long serviceId);
	
}
 