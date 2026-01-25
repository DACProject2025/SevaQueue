package com.sevaqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;

import com.sevaqueue.entity.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {

	List<Counter> findByServiceId(Long serviceId);
	
}
 
=======

import com.sevaqueue.entity.Counter;
import com.sevaqueue.entity.CounterStatus;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findByServiceId(Long serviceId);

    List<Counter> findByServiceIdAndStatus(Long serviceId, CounterStatus status);
}
>>>>>>> 48004d787c36e980746bf36827f47403342b9434
