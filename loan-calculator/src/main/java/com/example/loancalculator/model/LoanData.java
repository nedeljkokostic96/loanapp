package com.example.loancalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(schema = "loanapp", name = "loan_data")
@NoArgsConstructor
@AllArgsConstructor
public class LoanData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(name = "AMOUNT", nullable = false)
    private double amount;

    @Column(name = "INTEREST_RATE", nullable = false)
    private double interestRate;

    @Column(name = "LOAN_TERM", nullable = false)
    private int loanTerm;

    @Column(name = "TERM_TYPE", nullable = false)
    private char termType;

    @Column(name = "TOTAL_PAYMENT", nullable = false)
    private double totalPayment;

    @Column(name = "TOTAL_INTEREST", nullable = false)
    private double totalInterest;

    @Column(name = "MONTHLY_PAYMENT", nullable = false)
    private double monthlyPayment;

    @OneToMany(mappedBy = "loanData")
    private List<AmortizationSchedulePart> amortizationSchedulePartList;

}
