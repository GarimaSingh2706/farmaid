package com.examly.springapp.mapper;

import com.examly.springapp.dto.UserRequestDTO;
import com.examly.springapp.dto.UserResponseDTO;
import com.examly.springapp.model.User;

public class UserMapper {
	public static User toEntity(UserRequestDTO dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setMobileNumber(dto.getMobileNumber());
		user.setUserRole(dto.getUserRole());
		return user;
	}

	public static UserResponseDTO toDTO(User user) {
		UserResponseDTO dto = new UserResponseDTO();
		dto.setUserId(user.getUserId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setMobileNumber(user.getMobileNumber());
		dto.setUserRole(user.getUserRole());
		return dto;
	}
}
