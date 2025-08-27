package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long loanId;
	private String loanType;
	private String description;
	private double interestRate;
	private double maximumAmount;
	private int repaymentTenure;
	private String eligibility;
	private String documentsRequired;
	private boolean isdelete = false;
}