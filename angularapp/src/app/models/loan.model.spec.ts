import { Loan } from "./loan.model";
describe('Loan Model', () => {

  fit('Frontend_Loan_model_should_create_an_instance', () => {
    // Create a sample Loan object
    const loan: Loan = {
      loanId: 1,
      loanType: 'Home Loan',
      description: 'Loan for purchasing a new home',
      interestRate: 3.5,
      maximumAmount: 500000,
      repaymentTenure: 240,
      eligibility: 'Must have a stable income and good credit history',
      documentsRequired: 'Proof of income, credit report, ID proof'
    };

    expect(loan).toBeTruthy();
    expect(loan.loanType).toBe('Home Loan');
  });

});
