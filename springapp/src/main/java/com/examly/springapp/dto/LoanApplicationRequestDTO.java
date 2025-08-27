package com.examly.springapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationRequestDTO {

	@NotBlank(message = "State is required")
	private String state;  
	   
	@NotBlank(message = "District is required")
	private String district;  

	@NotBlank(message = "Farm location is required")
	private String farmLocation;

	@NotBlank(message = "Farmer address is required")
	private String farmerAddress;

	@Positive(message = "Farm size must be positive")
	private double farmSizeInAcres;

	@NotBlank(message = "Farm purpose is required")
	private String farmPurpose;

	@NotBlank(message = "File data is required")
	private String file;

}
