package com.sevaqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sevaqueue.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	
	@Query("""
			SELECT t FROM Token t 
			WHERE t.service.id = :serviceId
			AND t.status = 'WAITING'
			ORDER BY t.createdAt ASC
			""")
	List<Token> findWaitingTokens(Long serviceId);
	
	@Query("""
			SELECT COALESCE(MAX(t.tokenNumber), 0)
			FROM Token t
			WHERE t.service.id = :serviceId
			AND DATE(t.createdAt) = CURRENT_DATE 
			""")
	int findLastTokenNumber(Long serviceId);
}
