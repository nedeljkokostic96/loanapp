package com.example.loancalculator.service;

import com.example.loancalculator.controller.mapper.LoanDataResponseMapper;
import com.example.loancalculator.controller.request.LoanDataRequest;
import com.example.loancalculator.controller.response.AmortizationSchedulePartResponse;
import com.example.loancalculator.controller.response.LoanDataResponse;
import com.example.loancalculator.model.AmortizationSchedulePart;
import com.example.loancalculator.model.LoanData;
import com.example.loancalculator.repository.LoanDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.example.loancalculator.util.CalculatorUtil.calculateInterestPerMonth;

@Service
public class LoanDataService {

    private LoanDataRepository loanDataRepository;

    private AmortizationSchedulePartService amortizationSchedulePartService;

    @Autowired
    public void setLoanDataRepository(LoanDataRepository loanDataRepository) {
        this.loanDataRepository = loanDataRepository;
    }

    @Autowired
    public void setAmortizationSchedulePartService(AmortizationSchedulePartService amortizationSchedulePartService) {
        this.amortizationSchedulePartService = amortizationSchedulePartService;
    }

    public LoanDataResponse getLoanData(LoanDataRequest loanDataRequest) {
        LoanData loanData = calculateLoanData(loanDataRequest);
        return LoanDataResponseMapper.mapLoanDataDaoToLoanDataDto(loanData, loanData.getAmortizationSchedulePartList());
    }

    @Transactional
    public LoanDataResponse generateLoanDataWithAmortizationSchedule(LoanDataRequest loanDataRequest) {
        List<LoanData> loanDataList = loanDataRepository.getLoanDataByAmountInterestRateLoanTerm(loanDataRequest.getAmount(), loanDataRequest.getInterestRate(), loanDataRequest.getLoanTerm(), loanDataRequest.getTermType().charAt(0));
        if (!loanDataList.isEmpty()) {
            return LoanDataResponseMapper.mapLoanDataDaoToLoanDataDto(loanDataList.get(0), loanDataList.get(0).getAmortizationSchedulePartList());
        } else {
            LoanData loanData = calculateLoanData(loanDataRequest);
            LoanData saved = loanDataRepository.save(loanData);
            List<AmortizationSchedulePart> amortizationSchedule = amortizationSchedulePartService.createAmortizationSchedulePlan(saved);
            amortizationSchedulePartService.saveAll(amortizationSchedule);
            return LoanDataResponseMapper.mapLoanDataDaoToLoanDataDto(loanData, amortizationSchedule);
        }
    }

    public LoanData calculateLoanData(LoanDataRequest loanDataRequest) {
        int numberOfMonths = loanDataRequest.getTermType().equals("Y") ? loanDataRequest.getLoanTerm() * 12 : loanDataRequest.getLoanTerm();
        double monthlyPayment = calculatePaymentForOneMonth(numberOfMonths, loanDataRequest.getAmount(), loanDataRequest.getInterestRate());
        double totalPayment = BigDecimal.valueOf(numberOfMonths * monthlyPayment).setScale(2, RoundingMode.HALF_UP).doubleValue();
        double totalInterest = BigDecimal.valueOf(totalPayment - loanDataRequest.getAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        LoanData newLoanData = buildLoanDataObject(loanDataRequest, monthlyPayment, totalPayment, totalInterest);
        newLoanData.setAmortizationSchedulePartList(new ArrayList<>());
        return newLoanData;
    }

    public double calculatePaymentForOneMonth(int numberOfMonths, double amount, double interest) {
        BigDecimal interestPerMonth = calculateInterestPerMonth(interest);

        //(1 + i)^n part
        BigDecimal interestPowered = BigDecimal.valueOf(interestPerMonth.doubleValue() + 1.0).pow(numberOfMonths).setScale(6, RoundingMode.HALF_UP);

        //amount x i(1 + i)^n part
        BigDecimal amountWithInterest = interestPerMonth.multiply(BigDecimal.valueOf(amount).multiply(interestPowered)).setScale(6, RoundingMode.HALF_UP);

        //(1 + i)^n - 1 part
        BigDecimal interestForDivision = BigDecimal.valueOf(interestPowered.doubleValue() - 1.0).setScale(6, RoundingMode.HALF_UP);

        //main calculus
        return amountWithInterest.divide(interestForDivision, 2, RoundingMode.HALF_UP).doubleValue();
    }

    private LoanData buildLoanDataObject(LoanDataRequest loanDataRequest, double monthlyPayment, double totalPayment, double totalInterest) {
        LoanData newLoanData = new LoanData();
        newLoanData.setAmount(loanDataRequest.getAmount());
        newLoanData.setInterestRate(loanDataRequest.getInterestRate());
        newLoanData.setLoanTerm(loanDataRequest.getLoanTerm());
        newLoanData.setTermType(loanDataRequest.getTermType().charAt(0));
        newLoanData.setMonthlyPayment(monthlyPayment);
        newLoanData.setTotalInterest(totalInterest);
        newLoanData.setTotalPayment(totalPayment);
        return newLoanData;
    }

}
