package com.example.loancalculator.controller;

import com.example.loancalculator.controller.request.LoanDataRequest;
import com.example.loancalculator.controller.response.LoanDataResponse;
import com.example.loancalculator.service.LoanDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class LoanDataController {

    private LoanDataService loanDataService;

    @Autowired
    public void setLoanDataService(LoanDataService loanDataService) {
        this.loanDataService = loanDataService;
    }

    @GetMapping(value = "/calculate-loan-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLoanData(@RequestParam(value = "amount", required = true) double amount, @RequestParam(value = "interest", required = true) double interest, @RequestParam(value = "loanTerm", required = true) int loanTerm, @RequestParam(value = "termType", required = true) String termType) {
        LoanDataRequest loanDataRequest = new LoanDataRequest(amount, interest, loanTerm, termType);
        LoanDataResponse response = loanDataService.getLoanData(loanDataRequest);
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/generate-amortization-schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> generateAmortizationSchedule(@Valid @RequestBody LoanDataRequest loanDataRequest) {
        log.info("Request for loan calculation: {}", loanDataRequest);
        LoanDataResponse loanDataResponse = loanDataService.generateLoanDataWithAmortizationSchedule(loanDataRequest);
        log.info("Request calculated: {}", loanDataResponse);
        return ResponseEntity.status(201).body(loanDataResponse);
    }

}
