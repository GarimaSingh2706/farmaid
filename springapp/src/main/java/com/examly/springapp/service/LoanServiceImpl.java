package com.examly.springapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.examly.springapp.exception.LoanNotFoundException;
import com.examly.springapp.model.Loan;
import com.examly.springapp.repository.LoanRepo;
import com.examly.springapp.util.AppMessages;

@Service
public class LoanServiceImpl implements LoanService {
	private final LoanRepo loanRepo;

	public LoanServiceImpl(LoanRepo loanRepo) {
		this.loanRepo = loanRepo;
	}

	@Override
	public Loan addLoan(Loan loan) {
		return loanRepo.save(loan);
	}

	@Override
	public Loan getLoanById(long loanId) {
		Loan existingingLoan = loanRepo.findById(loanId).orElse(null);
		if (existingingLoan == null) {
			throw new LoanNotFoundException(String.format(AppMessages.LOAN_ID_NOT_EXIST, loanId));
		}
		return existingingLoan;
	}

	@Override
	public List<Loan> getAllLoan() {
		return loanRepo.findAll();

	}

	@Override
	public Loan updateLoan(long loanId, Loan updatedLoan) {
		Loan existingingLoan = loanRepo.findById(loanId).orElse(null);
		if (existingingLoan == null) {
			throw new LoanNotFoundException(String.format(AppMessages.LOAN_ID_NOT_EXIST, loanId));
		}
		updatedLoan.setLoanId(loanId);
		return loanRepo.save(updatedLoan);

	}


	@Override
	public List<Loan> viewAllLoans() {
		return loanRepo.findAll();
	}

	@Override
	public Loan loanActivateDeactive(long loanId, int status) {
		Loan existingingLoan = loanRepo.findById(loanId).orElse(null);
		if (existingingLoan == null) {
			throw new LoanNotFoundException(String.format(AppMessages.LOAN_ID_NOT_EXIST, loanId));
		}
		if(status==0)
			existingingLoan.setIsdelete(true);
		else
			existingingLoan.setIsdelete(false);
		existingingLoan= loanRepo.save(existingingLoan);
		return existingingLoan;
	}
}