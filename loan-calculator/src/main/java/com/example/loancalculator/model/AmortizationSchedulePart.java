package com.example.loancalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "loanapp", name = "AMORTIZATION_SCHEDULE")
@AllArgsConstructor
@NoArgsConstructor
public class AmortizationSchedulePart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(name = "PAYMENT_AMOUNT", nullable = false)
    private double paymentAmount;

    @Column(name = "PRINCIPAL_AMOUNT", nullable = false)
    private double principalAmount;

    @Column(name = "INTEREST_AMOUNT", nullable = false)
    private double interestAmount;

    @Column(name = "BALANCE_OWED", nullable = false)
    private double balanceOwed;

    @Column(name = "PAYMENT_ORDER", nullable = false)
    private int paymentOrder;

    @ManyToOne
    @JoinColumn(name = "LOAN_DATA_ID", nullable = false)
    private LoanData loanData;

}
