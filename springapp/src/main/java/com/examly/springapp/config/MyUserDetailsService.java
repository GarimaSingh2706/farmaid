package com.examly.springapp.config;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

import org.springframework.security.core.userdetails.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private final UserRepo userRepo;

	private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

	public MyUserDetailsService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("Loading user by email: {}", email);

		User user = userRepo.findByEmail(email);

		if (user == null) {
			logger.error("User not found with email: {}", email);
			throw new UsernameNotFoundException("User not found with email: " + email);
		}

		String role = "ROLE_" + user.getUserRole();
		logger.info("User found: {}, role: {}", user.getEmail(), role);

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(role)));
	}
}
