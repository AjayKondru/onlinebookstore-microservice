package com.org.mybookstore.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.mybookstore.api.user.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
	
	

 private String SECRET_KEY = "SECRET";

 public String extractUsername(String token) {
     return extractClaim(token, Claims::getSubject);
 }
 
 

 public Date extractExpiration(String token) {
     return extractClaim(token, Claims::getExpiration);
 }

 public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
     final Claims claims = extractAllClaims(token);
     return claimsResolver.apply(claims);
 }
 
 Claims extractAllClaims(String token) {
     return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
 }

 private Boolean isTokenExpired(String token) {
     return extractExpiration(token).before(new Date());
 }

 public String generateToken(User user) {
     Map<String, Object> claims = new HashMap<>();
     return createToken(claims, user);
 }

 private String createToken(Map<String, Object> claims, User user) {

     return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
             .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)).claim("role",user.getRole().name())
             .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
 }

 public Boolean validateToken(String token, String username) {
     final String usernameExtracted = extractUsername(token);
     return (usernameExtracted.equals(username) && !isTokenExpired(token));
 }
 
 public boolean validateJwtToken(String authToken) {
     try {
         Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
         return true;
     } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
     }
     return false;
 }
}
