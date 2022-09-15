package com.example.loancalculator.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class LoanDataRequest {

    @NotNull(message = "Amount can not be null!")
    @Min(value = 0, message = "Amount can not be negative!")
    private double amount;
    @NotNull(message = "Interest rate must be present!")
    @Min(value = 0, message = "Interest rate can not be negative!")
    private double interestRate;
    @NotNull(message = "Loan term must be present!")
    @Min(value = 0, message = "Loan term can not be negative!")
    private int loanTerm;
    @NotNull(message = "Loan term type must be present!")
    @Pattern(regexp = "Y|M", message = "Loan term type must be Y (years) or M (months)!")
    private String termType;

}
