package com.sevaqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sevaqueue.dto.OfficeResponseDto;
import com.sevaqueue.entity.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

    @Query("""
    SELECT new com.sevaqueue.dto.OfficeResponseDto(
        o.officeId,
        o.officeName,
        o.city,
        o.openTime,
        o.closeTime
    )
    FROM Office o
    WHERE o.active = true
    """)
    List<OfficeResponseDto> findActiveOffices();
}

