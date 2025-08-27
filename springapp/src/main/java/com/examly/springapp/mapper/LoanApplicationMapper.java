package com.examly.springapp.mapper;

import java.time.LocalDate;
import com.examly.springapp.dto.LoanApplicationRequestDTO;
import com.examly.springapp.dto.LoanApplicationResponseDTO;
import com.examly.springapp.model.Loan;
import com.examly.springapp.model.LoanApplication;
import com.examly.springapp.model.User;

public class LoanApplicationMapper {

	public static LoanApplication toEntity(LoanApplicationRequestDTO dto, User user, Loan loan) {
		LoanApplication entity = new LoanApplication();
		entity.setState(dto.getState());
		entity.setDistrict(dto.getDistrict());
		entity.setFarmLocation(dto.getFarmLocation());
		entity.setFarmerAddress(dto.getFarmerAddress());
		entity.setFarmSizeInAcres(dto.getFarmSizeInAcres());
		entity.setFarmPurpose(dto.getFarmPurpose());
		entity.setFile(dto.getFile());
		entity.setLoanStatus(0);
		entity.setSubmissionDate(LocalDate.now());
		entity.setUser(user);
		entity.setLoan(loan);
		return entity;
	}

	public static LoanApplicationResponseDTO toDTO(LoanApplication entity) {
		LoanApplicationResponseDTO dto = new LoanApplicationResponseDTO();
		dto.setLoanApplicationId(entity.getLoanApplicationId());
		dto.setSubmissionDate(entity.getSubmissionDate());
		dto.setLoanStatus(entity.getLoanStatus());
		dto.setState(entity.getState());
		dto.setDistrict(entity.getDistrict());
		dto.setFarmLocation(entity.getFarmLocation());
		dto.setFarmerAddress(entity.getFarmerAddress());
		dto.setFarmSizeInAcres(entity.getFarmSizeInAcres());
		dto.setFarmPurpose(entity.getFarmPurpose());
		dto.setFile(entity.getFile());
		dto.setUserResponseDTO(UserMapper.toDTO(entity.getUser()));
		dto.setLoanResponseDTO(LoanMapper.toDTO(entity.getLoan()));
		return dto;
	}
}
