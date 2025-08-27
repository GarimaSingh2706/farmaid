package com.examly.springapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDTO {
	@NotNull(message = "Loan type is required")
	private String loanType;

	@NotBlank(message = "Description is required")
	private String description;

	@Positive(message = "Interest Rate must be positive")
	private double interestRate;

	@Positive(message = "Maximum Amount must be positive")
	private double maximumAmount;

	@Positive(message = "Repayment Tenure must be positive")
	private int repaymentTenure;

	@NotBlank(message = "Eligibility is required")
	private String eligibility;

	@NotBlank(message = "Documents Required is required")
	private String documentsRequired;

	private boolean isdelete;
}