package com.ecommerce.jwt.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ecommerce.entity.Users;
import com.ecommerce.serviceImpl.UserServiceImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class SpringSecurityConfig {

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // This is used to basic user name and password
	/*
	 * @Bean UserDetailsService userDetailsService() { UserDetails adminUser =
	 * User.builder() .username("admin")
	 * .password(passwordEncoder().encode("admin")) .roles("ADMIN").build();
	 * 
	 * UserDetails normalUser = User.builder() .username("user")
	 * .password(passwordEncoder().encode("user")) .roles("USER").build();
	 * 
	 * return new InMemoryUserDetailsManager(adminUser, normalUser); }
	 */

 // This is used to load users from the TestUser table
    @Bean
    UserDetailsService userDetailsService(UserServiceImpl userService) {
        return username -> {
            Users user = userService.loadUserByUsername(username);
            if (user != null) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(bCryptPasswordEncoder().encode(user.getPassword()))
                        .roles("USER")
                        .build();
            }
            throw new UsernameNotFoundException("User not found");
        };
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}

