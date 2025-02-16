package com.ecommerce.serviceImpl;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.entity.TokenBlacklist;
import com.ecommerce.repository.TokenBlacklistRepository;
import com.ecommerce.service.TokenBlacklistService;

@Service
public class TokenBlacklistServiceImpl  implements TokenBlacklistService {

	
//	 @Autowired
//	    private TokenBlacklistRepository revokedTokenRepository;

//	    @Autowired
//	    private RedisTemplate<String, Object> redisTemplate;
	    
//	    @Autowired
//	    private RedisTemplate<String, String> redisTemplate;
	    
//	    private static final String BLACKLIST_PREFIX = "blacklist:";

		@Override
		public void isTokenBlacklisted(String token, long expirationMillis) {
			// TODO Auto-generated method stub
			
		}
	    
	
//		@Override
//		public void blacklistToken(String token, long expirationMillis) {
//			LocalDateTime expiryTime = LocalDateTime.now().plus(Duration.ofMillis(expirationMillis));
//
//	        // Save to PostgreSQL
//			TokenBlacklist blacklistedToken = new TokenBlacklist();
//	        blacklistedToken.setToken(token);
//	        blacklistedToken.setExpirationDate(expiryTime);
//	        revokedTokenRepository.save(blacklistedToken);
//
//	        // Save to Redis with expiration
//	        redisTemplate.opsForValue().set(token, "blacklisted", expirationMillis, TimeUnit.MILLISECONDS);
//			
//		}
//		@Override
//		  public boolean isTokenBlacklisted(String token) {
//		        // Check in Redis first
//		        Boolean isBlacklisted = redisTemplate.hasKey(token);
//		        if (Boolean.TRUE.equals(isBlacklisted)) {
//		            return true;
//		        }
//
//		        // Check in PostgreSQL as fallback
//		        return revokedTokenRepository.existsByToken(token);
//		    }


}
