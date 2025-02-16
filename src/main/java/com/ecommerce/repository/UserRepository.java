package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	Users findByUsername(String username);
	
}
