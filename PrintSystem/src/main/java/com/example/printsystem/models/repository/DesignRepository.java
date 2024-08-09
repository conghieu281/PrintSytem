package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignRepository extends JpaRepository<Design, Long> {
    List<Design> getAllByProjectId(Long projectId);
}
