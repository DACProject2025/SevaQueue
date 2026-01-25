package com.sevaqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sevaqueue.entity.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> {
 
}
