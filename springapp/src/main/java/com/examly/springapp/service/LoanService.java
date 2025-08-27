package com.examly.springapp.service;

import java.util.List;
import com.examly.springapp.model.Loan;

public interface LoanService {
	Loan addLoan(Loan loan);

	Loan getLoanById(long loanId);

	List<Loan> getAllLoan();

	Loan updateLoan(long loanId, Loan updatedLoan);

	List<Loan> viewAllLoans();

	Loan loanActivateDeactive(long loanId, int status);
}
