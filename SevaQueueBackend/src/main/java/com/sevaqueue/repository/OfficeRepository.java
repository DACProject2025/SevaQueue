package com.sevaqueue.repository;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sevaqueue.entity.Office;
import com.sevaqueue.entity.Service;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
	
	List<Service> findByOfficeId(Long officeId);

=======
import org.springframework.data.jpa.repository.JpaRepository;

import com.sevaqueue.entity.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> {
 
>>>>>>> 48004d787c36e980746bf36827f47403342b9434
}
