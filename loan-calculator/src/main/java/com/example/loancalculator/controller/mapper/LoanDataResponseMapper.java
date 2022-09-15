package com.example.loancalculator.controller.mapper;

import com.example.loancalculator.controller.response.AmortizationSchedulePartResponse;
import com.example.loancalculator.controller.response.LoanDataResponse;
import com.example.loancalculator.model.AmortizationSchedulePart;
import com.example.loancalculator.model.LoanData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanDataResponseMapper {

    public static LoanDataResponse mapLoanDataDaoToLoanDataDto(LoanData loanData, List<AmortizationSchedulePart> amortizationSchedule) {
        LoanDataResponse loanDataResponse = new LoanDataResponse();
        loanDataResponse.setMonthlyPayment(loanData.getMonthlyPayment());
        loanDataResponse.setTotalInterestRate(loanData.getTotalInterest());
        loanDataResponse.setTotalPayment(loanData.getTotalPayment());
        loanDataResponse.setAmortizationSchedulePartList(mapAmortizationScheduleDaoListToDtoList(amortizationSchedule));
        return loanDataResponse;
    }

    public static List<AmortizationSchedulePartResponse> mapAmortizationScheduleDaoListToDtoList(List<AmortizationSchedulePart> amortizationScheduleParts) {
        if (amortizationScheduleParts == null) return new ArrayList<>();
        return amortizationScheduleParts
                .stream()
                .map(x -> new AmortizationSchedulePartResponse(x.getId(), x.getPaymentAmount(), x.getPrincipalAmount(), x.getInterestAmount(), x.getBalanceOwed(), x.getPaymentOrder()))
                .collect(Collectors.toList());
    }

}
