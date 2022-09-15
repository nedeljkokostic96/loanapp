package com.example.loancalculator.controller;

import com.example.loancalculator.LoanCalculatorApplication;
import com.example.loancalculator.controller.request.LoanDataRequest;
import com.example.loancalculator.controller.response.LoanDataResponse;
import com.example.loancalculator.model.LoanData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LoanCalculatorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoanDataControllerTest {

    public static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCalculateLoan() {
        double amount = 30000;
        double interestRate = 5;
        int loanTerm = 36;
        String termType = "M";

        LoanDataResponse expected = new LoanDataResponse();
        expected.setMonthlyPayment(899.13);
        expected.setTotalInterestRate(2368.68);
        expected.setTotalPayment(32368.68);
        expected.setAmortizationSchedulePartList(new ArrayList<>());


        ResponseEntity<LoanDataResponse> result = restTemplate.getForEntity(BASE_URL + port + "/calculate-loan-data?amount=" + amount + "&interest=" + interestRate + "&loanTerm=" + loanTerm + "&termType=" + termType, LoanDataResponse.class);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(expected, result.getBody());
    }

    @Test
    void testGenerateAmortizationSchedule() {
        LoanDataRequest loanDataRequest = new LoanDataRequest(50000, 7, 10, "Y");

        ResponseEntity<LoanDataResponse> responseEntity = restTemplate.postForEntity(BASE_URL + port + "/generate-amortization-schedule", loanDataRequest, LoanDataResponse.class);

        assertEquals(201, responseEntity.getStatusCodeValue());
    }
}