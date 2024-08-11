package com.org.mybookstore.api.user;

import org.springframework.security.core.GrantedAuthority;

public enum role implements GrantedAuthority{
	USER, ADMIN;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.name();
	}
}
