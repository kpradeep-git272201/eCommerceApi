package com.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.dto.UserDto;
import com.ecommerce.entity.Users;

public interface UserService {
	
	public ResponseEntity<Object> addUser(Users user);
	
	public Users loadUserByUsername(String userName);
	
	public List<UserDto> getUsers();
}
