package com.blog.app.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.dao.entity.Role;
import com.blog.app.dao.entity.Role.ERole;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Optional<Role> findByName(ERole name);
	
}
