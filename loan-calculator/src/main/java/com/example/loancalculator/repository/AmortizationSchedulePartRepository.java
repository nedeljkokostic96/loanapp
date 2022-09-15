package com.example.loancalculator.repository;

import com.example.loancalculator.model.AmortizationSchedulePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmortizationSchedulePartRepository extends JpaRepository<AmortizationSchedulePart, Integer> {
}
