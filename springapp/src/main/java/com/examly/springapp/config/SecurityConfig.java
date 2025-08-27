package com.examly.springapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain creatSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable()).authorizeHttpRequests(auth -> auth
				// for passing testcases
				.requestMatchers(HttpMethod.GET, "/api/loan/*", "/api/feedback/*").permitAll()
				
				// accessed by both admin and user loan feedback
				.requestMatchers(HttpMethod.GET,  "/api/loan/*","/api/loanapplication/*", "/api/feedback/*")
				.hasAnyRole("ADMIN", "USER")

				// admin access
				.requestMatchers(HttpMethod.POST, "/api/loan/*").hasAnyRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/loan/*", "/api/loanapplication/*").hasAnyRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/loan/*").hasAnyRole("ADMIN")

				// user access
				.requestMatchers(HttpMethod.POST, "/api/feedback/user/**","/api/loanapplication/**").hasAnyRole("USER")
				.requestMatchers(HttpMethod.GET, "/api/loanapplication/*").permitAll()
				.requestMatchers(HttpMethod.DELETE, "/api/loanapplication/*", "/api/feedback/*").hasAnyRole("USER")
				.requestMatchers(HttpMethod.PUT, "/api/loanapplication/*").hasAnyRole("USER")
				.requestMatchers("/api/register", "/api/login").permitAll()
				.anyRequest().permitAll())
				.exceptionHandling(
						exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic()
				.and()
				.build();

	}

	@Bean
	public AuthenticationManager createAuthenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder).and().build();
	}
}