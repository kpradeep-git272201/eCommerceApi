package com.ecommerce.controller;

import lombok.AllArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.TokenBlacklist;
import com.ecommerce.entity.Users;
import com.ecommerce.jwt.util.JwtRequest;
import com.ecommerce.jwt.util.JwtResponse;
import com.ecommerce.jwt.util.JwtUtils;
import com.ecommerce.repository.TokenBlacklistRepository;
import com.ecommerce.service.TokenBlacklistService;
import com.ecommerce.service.UserService;

import jakarta.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtUtils helper;
	
	
	 @Autowired
	    private TokenBlacklistRepository tokenBlacklistRepository;
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody JwtRequest request) throws NoSuchAlgorithmException {

		/*
		 * authenticate convert plan password into md5 String
		 * md5Password=convertPwdMd5(request.getPassword());
		 */
		/* this.doAuthenticate(request.getUsername(), request.getPassword()); */
		this.doAuthenticate(request.getUsername(), request.getPassword());
		Users userDetails =userService.loadUserByUsername(request.getUsername());

		
	

		String token = this.helper.generateToken(userDetails);
		JwtResponse response = new JwtResponse(userDetails.getUsername(), token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void doAuthenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				username.toUpperCase(), password);
		try {
			manager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Credentials Invalid !!");
		}
	}

	/*
	 * private String convertPwdMd5(String password) throws NoSuchAlgorithmException
	 * { MessageDigest md = MessageDigest.getInstance("MD5"); byte[] hashInBytes =
	 * md.digest(password.getBytes()); StringBuilder sb = new StringBuilder(); for
	 * (byte b : hashInBytes) { sb.append(String.format("%02x", b)); } return
	 * sb.toString(); }
	 */
	 @PostMapping("/logout")
	    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
		   if (token.startsWith("Bearer ")) {
	            token = token.substring(7); // Remove "Bearer " prefix
	        }

	        if (!tokenBlacklistRepository.existsByToken(token)) {
	            tokenBlacklistRepository.save(new TokenBlacklist(token));
	        }

	        SecurityContextHolder.clearContext(); // Clear security context

	        return ResponseEntity.ok("Logged out successfully");
	    }
	
	@GetMapping("/test")
	public ResponseEntity<String> getTest() {

		return ResponseEntity.ok("I am Ok : v1-001");
	}
}
