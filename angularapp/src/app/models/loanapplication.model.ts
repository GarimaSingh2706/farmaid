export interface LoanApplication {
    loanApplicationId?: number;
    loanId?: number;
    submissionDate: string;
    loanStatus: number;
    farmLocation: string;
    farmerAddress: string;
    farmSizeInAcres: number;
    farmPurpose: string;
    file: string;
    state?: string;
    district?: string;
    userResponseDTO?: {
        userId: number;
        userName: string;
    };

}