package com.examly.springapp.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.examly.springapp.config.JwtUtils;
import com.examly.springapp.config.MyUserDetailsService;
import com.examly.springapp.dto.UserRequestDTO;
import com.examly.springapp.dto.UserResponseDTO;
import com.examly.springapp.mapper.UserMapper;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.pojo.ApiResponse;
import com.examly.springapp.service.UserService;
import com.examly.springapp.util.AppMessages;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AuthController {
	private final UserService userService;
	private final MyUserDetailsService myUserDetailsService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	public AuthController(UserService userService, MyUserDetailsService myUserDetailsService,
			AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		this.userService = userService;
		this.myUserDetailsService = myUserDetailsService;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		logger.info("Register request received: {}", userRequestDTO);
		User user = UserMapper.toEntity(userRequestDTO);
		user = userService.createUser(user);
		if (user != null) {
			UserResponseDTO userResponseDTO = UserMapper.toDTO(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
		}
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage(AppMessages.USER_ALREADY_EXISTS);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
		logger.info("Login attempt: {}", loginDTO);
		try {
			String username = loginDTO.getEmail();
			String password = loginDTO.getPassword();
			// Authenticate using Spring Security
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			// Load user from DB
			UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
			String token = jwtUtils.createToken(userDetails);
			User user = userService.loginUser(username);
			if (user != null) {
				logger.info("Login successful", user.getEmail());
				return ResponseEntity.ok(Map.of("token", token, "id", user.getUserId(), "role", user.getUserRole()));
			} else {
				logger.warn("User not found {}", username);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AppMessages.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Login failed for {}", loginDTO.getEmail(), e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AppMessages.INVALID_CREDENTIALS);
		}
	}

	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		logger.info("Fetching all users");
		List<User> users = userService.getAllUsers();
		if (users.isEmpty()) {
			logger.warn(AppMessages.NO_USERS_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.NO_USERS_FOUND);
		}
		List<UserResponseDTO> dtos = users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable long userId) {
		logger.info("Fetching user with ID: {}", userId);
		User user = userService.getUserById(userId);
		if (user == null) {
			logger.warn("User not found with ID: {}", userId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.USER_NOT_FOUND);
		}
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUserByUserId(@PathVariable long userId) {
		logger.info("Deleting user with ID: {}", userId);
		boolean deleted = userService.deleteUser(userId);
		if (deleted) {
			logger.info("User deleted successfully");
			return ResponseEntity.status(HttpStatus.OK).body(AppMessages.USER_DELETED);
		} else {
			logger.warn("User not found for deletion");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.USER_NOT_FOUND);
		}
	}
}
