package com.sevaqueue.repository;

<<<<<<< HEAD
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sevaqueue.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
=======
import org.springframework.data.jpa.repository.JpaRepository;

import com.sevaqueue.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

>>>>>>> 48004d787c36e980746bf36827f47403342b9434
}
