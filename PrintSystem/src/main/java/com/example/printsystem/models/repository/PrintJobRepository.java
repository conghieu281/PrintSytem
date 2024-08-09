package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.PrintJobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrintJobRepository extends JpaRepository<PrintJobs, Long> {
    List<PrintJobs> findAllByDesignId (Long id);
}
