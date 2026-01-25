package com.sevaqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sevaqueue.entity.OfficeService;

@Repository
public interface OfficeServiceRepository extends JpaRepository<OfficeService, Long> {

	
}
