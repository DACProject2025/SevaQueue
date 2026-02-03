package com.sevaqueueauthservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sevaqueueauthservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	java.util.List<User> findByRole(com.sevaqueueauthservice.entity.Role role);
}
