package com.example.loancalculator.controller.mapper;

import com.example.loancalculator.controller.response.AmortizationSchedulePartResponse;
import com.example.loancalculator.model.AmortizationSchedulePart;

public class AmortizationSchedulePartResponseMapper {
    public AmortizationSchedulePartResponse mapDaoToDto(AmortizationSchedulePart amortizationSchedulePart) {
        AmortizationSchedulePartResponse response = new AmortizationSchedulePartResponse();
        response.setPaymentOrder(amortizationSchedulePart.getPaymentOrder());
        response.setInterestAmount(amortizationSchedulePart.getInterestAmount());
        response.setPrincipalAmount(amortizationSchedulePart.getPrincipalAmount());
        response.setBalanceOwed(amortizationSchedulePart.getBalanceOwed());
        response.setPaymentAmount(amortizationSchedulePart.getPaymentAmount());
        return response;
    }
}
