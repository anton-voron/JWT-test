package com.voron.jwtapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.voron.jwtapp.entity.User;

public interface UserDAO extends JpaRepository<User, Integer> {
	
	@Query(value = "SELECT * FROM user u WHERE u.username = :username", nativeQuery = true)
	public User findByUsername(@Param("username") String username);
}
