import { LoanApplication } from "./loanapplication.model";

describe('LoanApplication Model', () => {

  fit('Frontend_LoanApplication_model_should_create_an_instance', () => {
    // Create a sample LoanApplication object
    const loanApplication: LoanApplication = {
      loanApplicationId: 1,
      userId: 123,
      loanId: 456,
      submissionDate: '2024-07-15',
      loanStatus: 1,
      farmLocation: 'Farmville, USA',
      farmerAddress: '123 Main St, Anytown, USA',
      farmSizeInAcres: 100,
      farmPurpose: 'Crop Farming',
      file: 'document.pdf'
    };

    expect(loanApplication).toBeTruthy();
    expect(loanApplication.loanApplicationId).toBeDefined();
    expect(loanApplication.userId).toBeDefined();
    expect(loanApplication.loanId).toBeDefined();
    expect(loanApplication.submissionDate).toBeDefined();
    expect(loanApplication.loanStatus).toBeDefined();
    expect(loanApplication.farmLocation).toBeDefined();
    expect(loanApplication.farmerAddress).toBeDefined();
    expect(loanApplication.farmSizeInAcres).toBeDefined();
    expect(loanApplication.farmPurpose).toBeDefined();
    expect(loanApplication.file).toBeDefined();
  });

});
