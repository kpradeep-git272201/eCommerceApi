package com.ecommerce.module.user.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.module.user.dto.UserDto;
import com.ecommerce.module.user.entity.Users;
import com.ecommerce.module.user.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@PostMapping("/addUser")
	public ResponseEntity<Object> addUser(@RequestBody Users user){
		return userService.addUser(user);
	}
	
	@GetMapping("/test")
	public ResponseEntity<Object> test() {
		return ResponseEntity.ok(new String("I'm running on port number : 8093"));
	}
	
	@GetMapping("/getUser")
	public List<UserDto> getUsers(){
		logger.info("This is an INFO log message");
        logger.warn("This is a WARNING log message");
        logger.error("This is an ERROR log message");
		return userService.getUsers();
	}
}
