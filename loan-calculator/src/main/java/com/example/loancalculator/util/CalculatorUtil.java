package com.example.loancalculator.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorUtil {

    public static BigDecimal calculateInterestPerMonth(double interestRate) {
        BigDecimal result = new BigDecimal(((interestRate / 100.0) / 12.0)).setScale(6, RoundingMode.HALF_UP);
        return result;
    }
}
