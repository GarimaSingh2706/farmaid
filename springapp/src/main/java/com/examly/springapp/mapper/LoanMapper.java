package com.examly.springapp.mapper;

import com.examly.springapp.dto.LoanRequestDTO;
import com.examly.springapp.dto.LoanResponseDTO;
import com.examly.springapp.model.Loan;

public class LoanMapper {
	public static Loan toEntity(LoanRequestDTO dto) {
		Loan loan = new Loan();
		loan.setLoanType(dto.getLoanType());
		loan.setDescription(dto.getDescription());
		loan.setInterestRate(dto.getInterestRate());
		loan.setMaximumAmount(dto.getMaximumAmount());
		loan.setRepaymentTenure(dto.getRepaymentTenure());
		loan.setEligibility(dto.getEligibility());
		loan.setDocumentsRequired(dto.getDocumentsRequired());
		return loan;
	}

	public static LoanResponseDTO toDTO(Loan loan) {
		LoanResponseDTO dto = new LoanResponseDTO();
		dto.setLoanId(loan.getLoanId());
		dto.setLoanType(loan.getLoanType());
		dto.setDescription(loan.getDescription());
		dto.setInterestRate(loan.getInterestRate());
		dto.setMaximumAmount(loan.getMaximumAmount());
		dto.setRepaymentTenure(loan.getRepaymentTenure());
		dto.setEligibility(loan.getEligibility());
		dto.setDocumentsRequired(loan.getDocumentsRequired());
		dto.setIsdelete(loan.isIsdelete());
		return dto;
	}
}