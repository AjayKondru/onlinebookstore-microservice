package com.org.mybookstore.api.util;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	@SneakyThrows
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
				.csrf(AbstractHttpConfigurer::disable).httpBasic();

		return http.build();

	}

	@Bean
	public JwtDecoder jwtDecoder() {
		String jwtKey = "cJX0WVR9amlBotu6mtsDJfoMybzCIDa1";
		SecretKey secretKey = new SecretKeySpec(jwtKey.getBytes(), "HMACSHA256");
		return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}

}
