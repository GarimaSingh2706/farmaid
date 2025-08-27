package com.examly.springapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Password is required")
	private String password;

	@NotBlank(message = "Username is required")
	private String username;

	@NotBlank(message = "Mobile Number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
	private String mobileNumber;

	@NotBlank(message = "User role is required")
	private String userRole;
}
