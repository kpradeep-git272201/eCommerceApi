package com.ecommerce.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="revoked_tokens", schema=com.ecommerce.util.ApplicationConstants.schemaName)
public class TokenBlacklist {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors, Getters, and Setters
    public TokenBlacklist() {}

    public TokenBlacklist(String token) {
        this.token = token;
        this.createdAt = LocalDateTime.now();
    }

    public String getToken() {
        return token;
    }
	
}
