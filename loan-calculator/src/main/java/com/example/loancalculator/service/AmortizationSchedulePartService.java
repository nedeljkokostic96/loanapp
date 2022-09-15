package com.example.loancalculator.service;

import com.example.loancalculator.model.AmortizationSchedulePart;
import com.example.loancalculator.model.LoanData;
import com.example.loancalculator.repository.AmortizationSchedulePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.example.loancalculator.util.CalculatorUtil.calculateInterestPerMonth;

@Service
public class AmortizationSchedulePartService {

    private AmortizationSchedulePartRepository amortizationSchedulePartRepository;

    @Autowired
    public void setAmortizationSchedulePartRepository(AmortizationSchedulePartRepository amortizationSchedulePartRepository) {
        this.amortizationSchedulePartRepository = amortizationSchedulePartRepository;
    }

    public void saveAll(List<AmortizationSchedulePart> amortizationScheduleParts) {
        amortizationSchedulePartRepository.saveAll(amortizationScheduleParts);
    }

    public  List<AmortizationSchedulePart> createAmortizationSchedulePlan(LoanData loanData) {
        BigDecimal interestRatePerMonth = calculateInterestPerMonth(loanData.getInterestRate());
        BigDecimal monthlyPaymentBigDecimal = BigDecimal.valueOf(loanData.getMonthlyPayment());
        List<AmortizationSchedulePart> amortizationSchedulePartList = new ArrayList<>();
        double oldAmount = loanData.getAmount();
        int counter = 1;
        while (oldAmount > 0) {
            BigDecimal currentMonthInterest = BigDecimal.valueOf(oldAmount).multiply(interestRatePerMonth).setScale(2, RoundingMode.HALF_UP);
            BigDecimal currentMonthPrincipalAmount = monthlyPaymentBigDecimal.subtract(currentMonthInterest).setScale(2, RoundingMode.HALF_UP);
            BigDecimal newAmount = BigDecimal.valueOf(oldAmount).subtract(currentMonthPrincipalAmount).setScale(2, RoundingMode.HALF_UP);
            oldAmount = newAmount.doubleValue() < 0 ? 0.0 : newAmount.doubleValue();
            AmortizationSchedulePart amortizationSchedulePartResponse = buildNewAmortizationSchedulePart(loanData, loanData.getMonthlyPayment(), currentMonthPrincipalAmount.doubleValue(), currentMonthInterest.doubleValue(), oldAmount, counter);
            amortizationSchedulePartList.add(amortizationSchedulePartResponse);
            counter++;
        }
        return amortizationSchedulePartList;
    }



    private AmortizationSchedulePart buildNewAmortizationSchedulePart(LoanData loanData, double monthlyPayment, double principalAmount, double interestAmount, double oldAmount, int paymentOrder) {
        AmortizationSchedulePart result = new AmortizationSchedulePart();
        result.setLoanData(loanData);
        result.setPaymentAmount(monthlyPayment);
        result.setBalanceOwed(oldAmount);
        result.setPrincipalAmount(principalAmount);
        result.setInterestAmount(interestAmount);
        result.setPaymentOrder(paymentOrder);
        return result;
    }

}
