package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.CustomerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerFeedBackRepository extends JpaRepository<CustomerFeedback, Long> {
}
