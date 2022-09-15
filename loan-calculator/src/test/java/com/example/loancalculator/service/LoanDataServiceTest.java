package com.example.loancalculator.service;

import com.example.loancalculator.controller.mapper.LoanDataResponseMapper;
import com.example.loancalculator.controller.request.LoanDataRequest;
import com.example.loancalculator.controller.response.LoanDataResponse;
import com.example.loancalculator.model.AmortizationSchedulePart;
import com.example.loancalculator.model.LoanData;
import com.example.loancalculator.repository.LoanDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanDataServiceTest {

    //test data
    double amount = 20000.0;
    double interest = 5.0;
    int loanTerm = 5;
    char termType = 'Y';
    double monthlyPayment = 377.43;
    double totalInterest = 2645.8;
    double totalPayment = 22645.8;

    @InjectMocks
    private LoanDataService sut; //service under test

    @Mock
    private LoanDataRepository loanDataRepository;

    @Mock
    private AmortizationSchedulePartService amortizationSchedulePartService;

    @Test
    void getLoanDataTEST() {
        //given
        LoanDataRequest loanDataRequest = getTestLoanDataRequest();
        LoanData loanData = getTestLoanData();
        LoanDataResponse expected = LoanDataResponseMapper.mapLoanDataDaoToLoanDataDto(loanData, loanData.getAmortizationSchedulePartList());

        //when
        LoanDataResponse result = sut.getLoanData(loanDataRequest);

        //then
        assertEquals(expected, result);
    }

    @Test
    void generateLoanDataWithAmortizationScheduleTEST_ifCalculusNotExists() {
        //given
        LoanDataRequest request = getTestLoanDataRequest();
        LoanData loanData = getTestLoanData();
        List<AmortizationSchedulePart> amortizationScheduleParts = new ArrayList<>();
        loanData.setAmortizationSchedulePartList(amortizationScheduleParts);
        LoanDataResponse loanDataResponse = LoanDataResponseMapper.mapLoanDataDaoToLoanDataDto(loanData, loanData.getAmortizationSchedulePartList());
        when(loanDataRepository.getLoanDataByAmountInterestRateLoanTerm(amount, interest, loanTerm, termType)).thenReturn(new ArrayList<>());
        when(loanDataRepository.save(loanData)).thenReturn(loanData);
        when(amortizationSchedulePartService.createAmortizationSchedulePlan(loanData)).thenReturn(amortizationScheduleParts);
        doNothing().when(amortizationSchedulePartService).saveAll(amortizationScheduleParts);


        //when
        LoanDataResponse testResult = sut.generateLoanDataWithAmortizationSchedule(request);

        //then
        verify(loanDataRepository, times(1)).getLoanDataByAmountInterestRateLoanTerm(amount, interest, loanTerm, termType);
        verify(loanDataRepository, times(1)).save(loanData);
        verify(amortizationSchedulePartService, times(1)).createAmortizationSchedulePlan(loanData);
        verify(amortizationSchedulePartService, times(1)).saveAll(amortizationScheduleParts);
        assertEquals(loanDataResponse, testResult);
    }

    @Test
    void generateLoanDataWithAmortizationScheduleTEST_ifCalculusExists() {
        //given
        LoanData loanData = getTestLoanData();
        loanData.setAmortizationSchedulePartList(new ArrayList<>());
        List<LoanData> loanDataList = new ArrayList<>();
        LoanDataResponse loanDataResponse = LoanDataResponseMapper.mapLoanDataDaoToLoanDataDto(loanData, loanData.getAmortizationSchedulePartList());
        loanDataList.add(loanData);
        when(loanDataRepository.getLoanDataByAmountInterestRateLoanTerm(amount, interest, loanTerm, termType)).thenReturn(loanDataList);

        //when
        LoanDataResponse testResult = sut.generateLoanDataWithAmortizationSchedule(getTestLoanDataRequest());

        //then
        assertEquals(loanDataResponse, testResult);
    }

    @Test
    void calculateLoanDataTEST() {
        //given
        LoanData loanData = getTestLoanData();
        loanData.setAmortizationSchedulePartList(new ArrayList<>());

        //when
        LoanData testResult = sut.calculateLoanData(getTestLoanDataRequest());

        //then
        assertEquals(loanData, testResult);
    }

    @Test
    void calculatePaymentForOneMonthTEST() {
        //given
        int numberOfMonths = 60;

        //when
        double testResult = sut.calculatePaymentForOneMonth(numberOfMonths, amount, interest);

        //then
        assertEquals(monthlyPayment, testResult);
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

    private LoanDataRequest getTestLoanDataRequest() {
        return new LoanDataRequest(amount, interest, loanTerm, String.valueOf(termType));
    }
}