package com.org.mybookstore.api.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.org.mybookstore.api.user.User;
import com.org.mybookstore.api.user.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	 private JwtUtil jwtUtils;
	
	 @Autowired
	 private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization")!=null?request.getHeader("Authorization").substring(7):"";
		if(token!=null && jwtUtils.validateJwtToken(token))
		{
			String username = jwtUtils.extractUsername(token);
			Claims claims = jwtUtils.extractAllClaims(token);
			String role = claims.get("role").toString();
			Optional<User> user = userService.findByUsername(username);
			Authentication auth = new UsernamePasswordAuthenticationToken( username,null,Arrays.asList(user.get().getRole()));
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(request, response);
		
	}

}
