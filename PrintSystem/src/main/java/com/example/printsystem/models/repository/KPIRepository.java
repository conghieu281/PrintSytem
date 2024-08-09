package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.KeyPerformanceIndicators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KPIRepository extends JpaRepository<KeyPerformanceIndicators, Long> {
}
