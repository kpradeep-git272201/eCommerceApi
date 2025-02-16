package com.ecommerce.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ecommerce.entity.Users;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
	@Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.issuer}")
    private String jwtIssuer;

    @Value("${app.jwt.validity-period}")
    private String validityPeriod;
    

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
  
    @SuppressWarnings("deprecation")
   	private Claims getAllClaimsFromToken(String token) {
           return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
       }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(Users userDetails) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("imenumber", imenumber); // Add imenumber as a claim
        return doGenerateToken(claims, userDetails.getUsername());
    }
    
    public Claims decodeJwt(String token) {
        try {
            // Decode the Base64-encoded secret
            byte[] decodedSecret = Base64.getDecoder().decode(jwtSecret);

            // Parse the JWT with the decoded secret key
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(decodedSecret)  // Use the decoded secret here
                    .build()
                    .parseClaimsJws(token)  // Parse the JWT token
                    .getBody();

            // Check if the token is expired
            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException("JWT token has expired");
            }

            return claims;
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    @SuppressWarnings("deprecation")
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		/*
		 * return Jwts.builder() .setClaims(claims) .setSubject(subject)
		 * .setIssuedAt(new Date(System.currentTimeMillis())) .setExpiration(new
		 * Date(System.currentTimeMillis() + Long.parseLong(validityPeriod))) //
		 * converting to milliseconds .signWith(SignatureAlgorithm.HS512, jwtSecret)
		 * .compact();
		 */
    	 return Jwts.builder()
    			 .setClaims(claims)
                 .setSubject(subject)
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(validityPeriod)))
                 .signWith(SignatureAlgorithm.HS256, jwtSecret)
                 .compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public Date getExpirationFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
}
