package com.examly.springapp.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponseDTO {
	private long loanApplicationId;
	private LocalDate submissionDate;
	private int loanStatus;
	private String state;  
	private String district;  
	private String farmLocation;
	private String farmerAddress;
	private double farmSizeInAcres;
	private String farmPurpose;
	private String file;
	private UserResponseDTO userResponseDTO;
	private LoanResponseDTO loanResponseDTO;
}
