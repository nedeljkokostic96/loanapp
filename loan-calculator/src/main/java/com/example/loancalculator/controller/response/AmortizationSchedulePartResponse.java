package com.example.loancalculator.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmortizationSchedulePartResponse {

    private int id;
    private double paymentAmount;
    private double principalAmount;
    private double interestAmount;
    private double balanceOwed;
    private int paymentOrder;
}
