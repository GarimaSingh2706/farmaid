package com.examly.springapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	private long userId;
	private String email;
	private String username;
	private String mobileNumber;
	private String userRole;
}
