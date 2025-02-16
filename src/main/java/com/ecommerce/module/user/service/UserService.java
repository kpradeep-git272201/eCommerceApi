package com.ecommerce.module.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.module.user.dto.UserDto;
import com.ecommerce.module.user.entity.Users;

public interface UserService {
	
	public ResponseEntity<Object> addUser(Users user);
	
	public Users loadUserByUsername(String userName);
	
	public List<UserDto> getUsers();
}
