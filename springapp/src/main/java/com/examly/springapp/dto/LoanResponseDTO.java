package com.examly.springapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDTO {
	private long loanId;
	private String loanType;
	private String description;
	private double interestRate;
	private double maximumAmount;
	private int repaymentTenure;
	private String eligibility;
	private String documentsRequired;
	private boolean isdelete;
}