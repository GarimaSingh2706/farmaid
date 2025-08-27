package com.examly.springapp.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoanApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long loanApplicationId;
	private LocalDate submissionDate;
	private int loanStatus;
	private String farmLocation;
	private String farmerAddress;
	private double farmSizeInAcres;
	private String farmPurpose;
	
	private String state;     
	private String district;  


	@Lob
	@Column(length = 10000000)
	private String file;
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	@ManyToOne
	@JoinColumn(name = "loanId")
	private Loan loan;
}
