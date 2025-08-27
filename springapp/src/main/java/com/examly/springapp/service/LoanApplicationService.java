package com.examly.springapp.service;

import java.util.List;
import com.examly.springapp.model.LoanApplication;

public interface LoanApplicationService {
    LoanApplication getLoanApplicationById(Long loanApplicationId);

    List<LoanApplication> getLoanApplicationsByUserId(Long userId);

    List<LoanApplication> getAllLoanApplications();

    LoanApplication updateLoanApplication(Long loanApplicationId, LoanApplication loanApplication);

    LoanApplication deleteLoanApplication(Long loanApplicationId);

    LoanApplication addLoanApplication(long userId, LoanApplication loanApplication, long loanId);

    LoanApplication updateStatus(Long loanApplicationId, int status);
}
