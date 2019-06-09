package com.purpletalk.hr.project.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	
	//Method 1
	
//	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
//
//    @Value("${app.jwtSecret}")
//    private String jwtSecret;
//
//    @Value("${app.jwtExpirationInMs}")
//    private int jwtExpirationInSec;
//    
//    //generating Token
//    public String generateToken(Authentication authentication) {
//
//    	HrDetails userPrincipal = (HrDetails) authentication.getPrincipal();
//    	System.out.println(userPrincipal.getUsername()+">>>>>>>>>>>");
//        return Jwts.builder()
//                .setSubject(userPrincipal.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInSec*1000))
//                .signWith(SignatureAlgorithm.HS256, jwtSecret)
//                .compact();
//    }
//    //getting username from token    
//    public String getUserNameFromJWT(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }
//    //Validating  Tokens
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            return true;
//        } catch (SignatureException ex) {
//            logger.error("Invalid JWT signature");
//        } catch (MalformedJwtException ex) {
//            logger.error("Invalid JWT token");
//        } catch (ExpiredJwtException ex) {
//            logger.error("Expired JWT token");
//        } catch (UnsupportedJwtException ex) {
//            logger.error("Unsupported JWT token");
//        }
//        catch (IllegalArgumentException ex) {
//            logger.error("JWT claims string is empty.");
//        }
//        return false;
//    }
//    
    
	//Method 2
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	@Value("${app.jwtSecret}")
	private String secret;
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
	    //for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
	return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	//check if the token has expired
	private Boolean isTokenExpired(String token) {
	final Date expiration = getExpirationDateFromToken(token);
	return expiration.before(new Date());
	}
	//generate token for user
	public String generateToken(UserDetails userDetails) {
	Map<String, Object> claims = new HashMap<>();
	return doGenerateToken(claims, userDetails.getUsername());
	}
	//while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string 
	private String doGenerateToken(Map<String, Object> claims, String subject) {
	return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	//validate token
	public boolean validateToken(String jwtToken, UserDetails userDetails) {
		// TODO Auto-generated method stub
		final String username = getUsernameFromToken(jwtToken);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
	}
	
	
	
	
    

    

}
