package com.examly.springapp.controller;

import com.examly.springapp.dto.LoanApplicationRequestDTO;
import com.examly.springapp.dto.LoanApplicationResponseDTO;
import com.examly.springapp.mapper.LoanApplicationMapper;
import com.examly.springapp.model.Loan;
import com.examly.springapp.model.LoanApplication;
import com.examly.springapp.model.User;
import com.examly.springapp.service.LoanApplicationService;
import com.examly.springapp.service.LoanService;
import com.examly.springapp.service.UserService;
import com.examly.springapp.util.AppMessages;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/loanapplication")
public class LoanApplicationController {
	private final LoanApplicationService loanApplicationService;
	private final UserService userService;
	private final LoanService loanService;
	private static final Logger logger = LoggerFactory.getLogger(LoanApplicationController.class);

	public LoanApplicationController(LoanApplicationService loanApplicationService, UserService userService,
			LoanService loanService) {
		this.loanApplicationService = loanApplicationService;
		this.userService = userService;
		this.loanService = loanService;
	}

	@PostMapping("/{userId}/{loanId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addLoanApplication(@PathVariable long userId, @PathVariable long loanId,
			@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
		logger.info("Creating loan application for user {} and loan {}", userId, loanId);
		User user = userService.getUserById(userId);
		Loan loan = loanService.getLoanById(loanId);
		if (user == null || loan == null) {
			logger.warn(AppMessages.USER_OR_LOAN_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.USER_OR_LOAN_NOT_FOUND);
		}
		LoanApplication entity = LoanApplicationMapper.toEntity(loanApplicationRequestDTO, user, loan);
		LoanApplication saved = loanApplicationService.addLoanApplication(userId, entity, loanId);
		if (saved != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(LoanApplicationMapper.toDTO(saved));
		} else {
			logger.error("Failed to save loan application");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AppMessages.LOAN_APPLICATION_CREATION_FAILED);
		}
	}

	@GetMapping("/{loanApplicationId}")
	public ResponseEntity<?> getLoanApplicationById(@PathVariable Long loanApplicationId) {
		logger.info("Fetching loan application with ID: {}", loanApplicationId);
		LoanApplication entity = loanApplicationService.getLoanApplicationById(loanApplicationId);
		if (entity != null) {
			LoanApplicationResponseDTO responseDTO = LoanApplicationMapper.toDTO(entity);
			return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.LOAN_APPLICATION_NOT_FOUND);
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getLoanApplicationsByUserId(@PathVariable Long userId) {
		logger.info("Fetching loan application for User with ID: {}", userId);
		List<LoanApplication> loanApplicationList = loanApplicationService.getLoanApplicationsByUserId(userId);
		if (loanApplicationList != null) {
			List<LoanApplicationResponseDTO> responseList = loanApplicationList.stream()
					.map(LoanApplicationMapper::toDTO).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(responseList);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.NO_LOAN_APPLICATIONS_FOR_USER);
		}
	}

	@GetMapping
	public ResponseEntity<?> getAllLoanApplications() {
		logger.info("Fetching all loan applications");
		List<LoanApplication> list = loanApplicationService.getAllLoanApplications();
		if (list != null && !list.isEmpty()) {
			List<LoanApplicationResponseDTO> responseList = list.stream().map(LoanApplicationMapper::toDTO)
					.collect(Collectors.toList());
			return ResponseEntity.ok(responseList);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.LOAN_APPLICATION_NOT_FOUND);
		}
	}

	@PutMapping("/{loanApplicationId}")
	public ResponseEntity<?> updateLoanApplication(@PathVariable Long loanApplicationId,
			@Valid @RequestBody LoanApplicationRequestDTO requestDTO) {
		logger.info("Updating loan application ID {}", loanApplicationId);
		LoanApplication old = loanApplicationService.getLoanApplicationById(loanApplicationId);
		if (old == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.LOAN_APPLICATION_NOT_FOUND);
		}

		old.setState(requestDTO.getState());
		old.setDistrict(requestDTO.getDistrict());
		old.setFarmLocation(requestDTO.getFarmLocation());
		old.setFarmerAddress(requestDTO.getFarmerAddress());
		old.setFarmSizeInAcres(requestDTO.getFarmSizeInAcres());
		old.setFarmPurpose(requestDTO.getFarmPurpose());
		old.setFile(requestDTO.getFile());
		LoanApplication updated = loanApplicationService.updateLoanApplication(loanApplicationId, old);
		LoanApplicationResponseDTO responseDTO = LoanApplicationMapper.toDTO(updated);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

	@DeleteMapping("/{loanApplicationId}")
	public ResponseEntity<?> deleteLoanApplication(@PathVariable Long loanApplicationId) {
		logger.info("Deleting loan application ID {}", loanApplicationId);
		System.out.println("Application status : " + loanApplicationId);
		LoanApplication deleted = loanApplicationService.deleteLoanApplication(loanApplicationId);
		if (deleted != null) {
			LoanApplicationResponseDTO responseDTO = LoanApplicationMapper.toDTO(deleted);
			return ResponseEntity.ok(responseDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.LOAN_APPLICATION_NOT_FOUND);
		}
	}

	@PutMapping
	public ResponseEntity<?> updateStatus(@RequestParam Long loanApplicationId, @RequestParam int status) {
		logger.info("Updating status of loan application ID {} to {}", loanApplicationId, status);
		LoanApplication updated = loanApplicationService.updateStatus(loanApplicationId, status);
		if (updated != null) {
			LoanApplicationResponseDTO responseDTO = LoanApplicationMapper.toDTO(updated);
			return ResponseEntity.ok(responseDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.LOAN_APPLICATION_NOT_FOUND);
		}
	}
}