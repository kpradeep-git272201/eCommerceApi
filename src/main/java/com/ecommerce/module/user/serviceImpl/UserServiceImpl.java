package com.ecommerce.module.user.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.module.user.dto.UserDto;
import com.ecommerce.module.user.entity.Users;
import com.ecommerce.module.user.repository.UserRepository;
import com.ecommerce.module.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService{

	private final ObjectMapper objectMapper;
	@Autowired
	private UserRepository userRepository;
	
	   public UserServiceImpl(ObjectMapper objectMapper) {
	        this.objectMapper = objectMapper;
	    }
	   
	   
	@Override
	public ResponseEntity<Object> addUser(Users user) {
		userRepository.save(user);
		return ResponseEntity.ok(new String("User added successfully!"));
	}
	@Override
    public Users loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
	@Override
	public List<UserDto> getUsers() {
		List<Users> all = userRepository.findAll();
		List<UserDto> asList = Arrays.asList(objectMapper.convertValue(all, UserDto[].class));
		return asList;
	}
}
