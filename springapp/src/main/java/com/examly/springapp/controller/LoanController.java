package com.examly.springapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.examly.springapp.dto.LoanRequestDTO;
import com.examly.springapp.dto.LoanResponseDTO;
import com.examly.springapp.mapper.LoanMapper;
import com.examly.springapp.model.Loan;
import com.examly.springapp.service.LoanService;
import com.examly.springapp.util.AppMessages;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/loan")
public class LoanController {
	private final LoanService loanService;
	private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@PostMapping
	public ResponseEntity<?> addLoan(@RequestBody @Valid LoanRequestDTO requestDTO) {
		logger.info("Creating new loan of type: {}", requestDTO.getLoanType());
		Loan loan = LoanMapper.toEntity(requestDTO);
		Loan saved = loanService.addLoan(loan);
		LoanResponseDTO responseDTO = LoanMapper.toDTO(saved);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

	@GetMapping("/{loanId}")
	public ResponseEntity<?> getLoanById(@PathVariable long loanId) {
		logger.info("Fetching loan with ID {}", loanId);
		Loan loan = loanService.getLoanById(loanId);
		if (loan != null) {
			LoanResponseDTO responseDTO = LoanMapper.toDTO(loan);
			return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.LOAN_NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<?> getAllLoans() {
		logger.info("Fetching all loans");
		List<Loan> loans = loanService.getAllLoan();
		List<LoanResponseDTO> responseList = loans.stream().map(LoanMapper::toDTO).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}

	@PutMapping("/{loanId}")
	public ResponseEntity<?> updateLoan(@PathVariable long loanId, @Valid @RequestBody LoanRequestDTO requestDTO) {
		logger.info("Updating loan with ID {}", loanId);
		Loan updatedEntity = LoanMapper.toEntity(requestDTO);
		Loan updatedLoan = loanService.updateLoan(loanId, updatedEntity);
		if (updatedLoan != null) {
			LoanResponseDTO responseDTO = LoanMapper.toDTO(updatedLoan);
			return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppMessages.LOAN_UPDATE_FAILED);
		}
	}

	@PutMapping("/activate/{loanId}/{status}")
	public ResponseEntity<?> loanActivateDeactive(@PathVariable long loanId, @PathVariable int status) {
		if(status==0)
		    logger.info("Make loan with ID {} deactivate", loanId);
		else
		    logger.info("Make loan with ID {} activate", loanId);
		Loan deleted = loanService.loanActivateDeactive(loanId,status);
		if (deleted != null) {
			LoanResponseDTO responseDTO = LoanMapper.toDTO(deleted);
			return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.LOAN_NOT_FOUND);
		}
	}

	
}
