package com.ecommerce.service;

import java.time.Instant;

public interface TokenBlacklistService {
//    void blacklistToken(String token, Instant expiration);
//    boolean isTokenBlacklisted(String token);
    
    public void isTokenBlacklisted(String token, long expirationMillis);
}
