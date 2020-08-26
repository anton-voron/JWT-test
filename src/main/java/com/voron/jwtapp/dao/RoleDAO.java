package com.voron.jwtapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.voron.jwtapp.entity.Role;

public interface RoleDAO extends JpaRepository<Role, Integer> {
	
	@Query(value = "SELECT * FROM role r WHERE r.role_title = :roleTitle", nativeQuery = true)
	public Role findByRoleTitle(@Param("roleTitle") String roleTitle);
}
