package com.sevaqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sevaqueue.entity.Office;
import com.sevaqueue.entity.OfficeService;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
	
	List<Office> findByActiveTrue();
	
	List<OfficeService> findByOfficeId(Long officeId);

}

