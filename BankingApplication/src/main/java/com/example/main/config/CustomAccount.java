package com.example.main.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.main.entity.Account;

public class CustomAccount implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private Account account;
	
	public CustomAccount(Account account) {
		this.account = account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(account.getRole());
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		
		return account.getUsername();
	}

}
