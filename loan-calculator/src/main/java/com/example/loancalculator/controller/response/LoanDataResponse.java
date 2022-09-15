package com.example.loancalculator.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDataResponse {

    private double monthlyPayment;
    private double totalInterestRate;
    private double totalPayment;
    private List<AmortizationSchedulePartResponse> amortizationSchedulePartList;

}
