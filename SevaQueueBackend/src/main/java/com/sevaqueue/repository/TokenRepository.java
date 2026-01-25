package com.sevaqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sevaqueue.entity.Token;
import com.sevaqueue.entity.User;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	// 1️ Get waiting tokens for a service (queue order)
    @Query("""
        SELECT t FROM Token t
        WHERE t.service.id = :serviceId
          AND t.status = 'WAITING'
        ORDER BY t.createdAt ASC
    """)
    List<Token> findWaitingTokensByService(@Param("serviceId") Long serviceId);

    // 2️ Get last token number for a service (daily reset)
    @Query("""
        SELECT COALESCE(MAX(t.tokenNumber), 0)
        FROM Token t
        WHERE t.service.id = :serviceId
          AND t.createdAt >= CURRENT_DATE
    """)
    int findLastTokenNumber(@Param("serviceId") Long serviceId);

	List<Token> findByUser(User user);

	@Query("""
			SELECT t.tokenNumber
			FROM Token t
			WHERE t.service.serviceId = :serviceId
			  AND t.user.id = :userid
			  AND t.status = 'WAITING'
			""")
	Integer findUserTokenNumber(@Param("serviceId") Long serviceId, 
								@Param("userId") Long id);
	
	@Query("""
			SELECT t FROM Token t
			WHERE t.service.serviceId = :serviceId
			AND DATE(t.createdAt) = CURRENT_DATE
			ORDER BY t.createdAt
			""")
	List<Token> findTodayTokens(Long serviceId);
	
	List<Token> findByServiceServiceIdOrderByCreatedAt(Long serviceId);


}
