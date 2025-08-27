package com.examly.springapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.examly.springapp.exception.ApplicationStatusCancelledException;
import com.examly.springapp.exception.LoanNotFoundException;
import com.examly.springapp.exception.LoanApplicationNotFoundException;
import com.examly.springapp.exception.UserNotFound;
import com.examly.springapp.model.Loan;
import com.examly.springapp.model.LoanApplication;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.LoanApplicationRepo;
import com.examly.springapp.repository.LoanRepo;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.util.AppMessages;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {
	private final LoanApplicationRepo loanApplicationRepo;
	private final UserRepo userRepo;
	private final LoanRepo loanRepo;

	public LoanApplicationServiceImpl(LoanApplicationRepo loanApplicationRepo, UserRepo userRepo, LoanRepo loanRepo) {
		this.loanApplicationRepo = loanApplicationRepo;
		this.userRepo = userRepo;
		this.loanRepo = loanRepo;
	}

	@Override
	public LoanApplication addLoanApplication(long userId, LoanApplication loanApplication, long loanId) {
		User existingUser = userRepo.findById(userId).orElse(null);
		if (existingUser == null)
			throw new UserNotFound(AppMessages.USER_NOT_FOUND);
		Loan existingLoan = loanRepo.findById(loanId).orElse(null);
		if (existingLoan == null)
			throw new LoanNotFoundException(AppMessages.LOAN_NOT_FOUND);

		loanApplication.setLoanStatus(0);
		loanApplication.setUser(existingUser);
		loanApplication.setLoan(existingLoan);
		return loanApplicationRepo.save(loanApplication);
	}

	@Override
	public LoanApplication getLoanApplicationById(Long loanApplicationId) {
		return loanApplicationRepo.findById(loanApplicationId).orElse(null);
	}

	@Override
	public List<LoanApplication> getLoanApplicationsByUserId(Long userId) {
		User user = userRepo.findById(userId).orElse(null);
		if (user == null) {
			throw new UserNotFound(AppMessages.USER_NOT_FOUND);
		}
		return loanApplicationRepo.getLoanApplicationsByUserId(userId);

	}

	@Override
	public List<LoanApplication> getAllLoanApplications() {
		return loanApplicationRepo.findAll();
	}

	@Override
	public LoanApplication updateLoanApplication(Long loanApplicationId, LoanApplication loanApplication) {
		LoanApplication existingLoanApplication = loanApplicationRepo.findById(loanApplicationId).orElse(null);
		if (existingLoanApplication == null) {
			throw new LoanApplicationNotFoundException(AppMessages.LOAN_APPLICATION_NOT_FOUND);
		}
		loanApplication.setLoanApplicationId(loanApplicationId);
		return loanApplicationRepo.save(loanApplication);
	}

	@Override
	public LoanApplication deleteLoanApplication(Long loanApplicationId) {
		LoanApplication existingLoanApplication = loanApplicationRepo.findById(loanApplicationId).orElse(null);
		if (existingLoanApplication == null) {
			throw new LoanApplicationNotFoundException(AppMessages.LOAN_APPLICATION_NOT_FOUND);
		}
		if (existingLoanApplication.getLoanStatus() == 3) {
			throw new ApplicationStatusCancelledException(AppMessages.APPLICATION_ALREADY_CANCELLED);
		}
		if (existingLoanApplication.getLoanStatus() == 1 || existingLoanApplication.getLoanStatus() == 2) {
			throw new ApplicationStatusCancelledException(AppMessages.APPLICATION_CANNOT_BE_CANCELLED);
		}
		if (existingLoanApplication.getLoanStatus() == 0) {
			existingLoanApplication.setLoanStatus(3);
			return loanApplicationRepo.save(existingLoanApplication);
		}
		return null;
	}

	@Override
	public LoanApplication updateStatus(Long loanApplicationId, int status) {
		LoanApplication existingLoanApplication = loanApplicationRepo.findById(loanApplicationId).orElse(null);
		if (existingLoanApplication == null) {
			throw new LoanNotFoundException(
					String.format(AppMessages.LOAN_APPLICATION_ID_NOT_EXIST, loanApplicationId));
		}
		if (existingLoanApplication.getLoanStatus() == 3) {
			throw new ApplicationStatusCancelledException(AppMessages.APPLICATION_ALREADY_CANCELLED);
		}
		if (existingLoanApplication.getLoanStatus() == 0) {
			existingLoanApplication.setLoanStatus(status);
			return loanApplicationRepo.save(existingLoanApplication);
		}
		return null;
	}
}
