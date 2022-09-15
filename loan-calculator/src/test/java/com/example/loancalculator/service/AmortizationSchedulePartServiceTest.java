package com.example.loancalculator.service;

import com.example.loancalculator.model.AmortizationSchedulePart;
import com.example.loancalculator.model.LoanData;
import com.example.loancalculator.repository.AmortizationSchedulePartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AmortizationSchedulePartServiceTest {

    //test data
    double amount = 500;
    double interest = 5;
    int loanTerm = 2;
    char termType = 'M';
    double monthlyPayment = 251.57;
    double totalPayment = 503.14;
    double totalInterest = 3.14;

    @InjectMocks
    private AmortizationSchedulePartService sut;

    @Test
    void createAmortizationSchedulePlanTEST() {
        //given
        LoanData loanData = getTestLoanData();
        List<AmortizationSchedulePart> expected = new ArrayList<>();
        AmortizationSchedulePart amsp1 = new AmortizationSchedulePart();
        amsp1.setPaymentAmount(monthlyPayment);
        amsp1.setPrincipalAmount(249.49);
        amsp1.setInterestAmount(2.08);
        amsp1.setBalanceOwed(250.51);
        amsp1.setPaymentOrder(1);
        amsp1.setLoanData(loanData);
        expected.add(amsp1);
        AmortizationSchedulePart amsp2 = new AmortizationSchedulePart();
        amsp2.setPaymentAmount(monthlyPayment);
        amsp2.setPrincipalAmount(250.53);
        amsp2.setInterestAmount(1.04);
        amsp2.setBalanceOwed(0.0);
        amsp2.setPaymentOrder(2);
        amsp2.setLoanData(loanData);
        expected.add(amsp2);

        //when
        List<AmortizationSchedulePart> testResult = sut.createAmortizationSchedulePlan(loanData);

        //then
        assertEquals(expected, testResult);
    }

    private LoanData getTestLoanData() {
        LoanData loanData = new LoanData();
        loanData.setAmount(amount);
        loanData.setInterestRate(interest);
        loanData.setLoanTerm(loanTerm);
        loanData.setTermType(termType);
        loanData.setMonthlyPayment(monthlyPayment);
        loanData.setTotalInterest(totalInterest);
        loanData.setTotalPayment(totalPayment);
        return loanData;
    }
}