package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.ImportCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportCouponRepository extends JpaRepository<ImportCoupon, Long> {
}
