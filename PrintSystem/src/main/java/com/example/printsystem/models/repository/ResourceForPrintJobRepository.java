package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.ResourceForPrintJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceForPrintJobRepository extends JpaRepository<ResourceForPrintJob, Long> {
}
