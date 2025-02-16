package com.ecommerce.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecommerce.repository.TokenBlacklistRepository;

@Service
public class TokenCleanupService {

//    @Autowired
//    private TokenBlacklistRepository tokenRepository;

    @Scheduled(fixedRate = 3600000) // Runs every hour
    public void cleanExpiredTokens() {
//        LocalDateTime now = LocalDateTime.now();
//        tokenRepository.deleteAll(tokenRepository.findAll()
//                .stream()
//                .filter(token -> token.getExpirationDate().isBefore(now))
//                .toList());
    }
}
