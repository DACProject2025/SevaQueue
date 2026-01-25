package com.sevaqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sevaqueue.entity.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
 

   
}
