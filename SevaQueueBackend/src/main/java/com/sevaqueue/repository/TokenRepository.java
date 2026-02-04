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

	@Query("""
			    SELECT t FROM Token t
			    WHERE t.service.serviceId = :serviceId
			      AND t.status = 'CALLED'
			    ORDER BY t.createdAt DESC
			""")
	List<Token> findCalledTokensByService(@Param("serviceId") Long serviceId);

	// Get waiting tokens for a service (queue order)
	@Query("""
			    SELECT t FROM Token t
			    WHERE t.service.serviceId = :serviceId
			      AND t.status = 'WAITING'
			    ORDER BY t.createdAt ASC
			""")
	List<Token> findWaitingTokensByService(@Param("serviceId") Long serviceId);

	// Get last token number for a service (daily reset)
	@Query("""
			    SELECT COALESCE(MAX(t.tokenNumber), 0)
			    FROM Token t
			    WHERE t.service.serviceId = :serviceId
			      AND t.createdAt >= CURRENT_DATE
			""")
	int findLastTokenNumber(@Param("serviceId") Long serviceId);

	@Query("""
			SELECT t FROM Token t
			WHERE t.user = :user
			ORDER BY t.createdAt DESC
			""")
	List<Token> findByUser(@Param("user") User user);

	@Query("""
			SELECT t.tokenNumber
			FROM Token t
			WHERE t.service.serviceId = :serviceId
			  AND t.user.id = :userid
			  AND t.status = 'WAITING'
			""")
	Integer findUserTokenNumber(@Param("serviceId") Long serviceId,
			@Param("userid") Long id);

	@Query("""
			SELECT t FROM Token t
			WHERE t.service.serviceId = :serviceId
			AND DATE(t.createdAt) = CURRENT_DATE
			ORDER BY t.createdAt ASC
			""")
	List<Token> findTodayTokens(@Param("serviceId") Long serviceId);

	List<Token> findByServiceServiceIdOrderByCreatedAtDesc(Long serviceId);

	@Query("""
			SELECT COUNT(t)
			FROM Token t
			WHERE t.service.serviceId = :serviceId
			AND DATE(t.createdAt) = CURRENT_DATE
			""")
	int countTodayTokens(@Param("serviceId") Long serviceId);

	@org.springframework.data.jpa.repository.Modifying
	@org.springframework.transaction.annotation.Transactional
	@Query("""
			UPDATE Token t
			SET t.status = 'EXPIRED'
			WHERE t.createdAt < CURRENT_DATE
			AND (t.status = 'WAITING' OR t.status = 'CALLED')
			""")
	int updateExpiredTokens();
}
