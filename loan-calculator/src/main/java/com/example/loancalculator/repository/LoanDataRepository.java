package com.example.loancalculator.repository;

import com.example.loancalculator.model.LoanData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanDataRepository extends JpaRepository<LoanData, Integer> {

    @Query("SELECT l FROM LoanData l WHERE l.amount = :amount AND l.interestRate = :interestRate AND l.loanTerm = :loanTerm AND l.termType = :termType")
    List<LoanData> getLoanDataByAmountInterestRateLoanTerm(@Param("amount") double amount, @Param("interestRate") double interestRate, @Param("loanTerm") int loanTerm, @Param("termType") char termType);

}
