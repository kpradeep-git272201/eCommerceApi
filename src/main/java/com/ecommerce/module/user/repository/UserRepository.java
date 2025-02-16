package com.ecommerce.module.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.module.user.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	Users findByUsername(String username);
	
}
