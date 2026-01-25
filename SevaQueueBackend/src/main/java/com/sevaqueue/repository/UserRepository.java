package com.sevaqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sevaqueue.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
