package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.ResourcePropertyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcePropertyDetailRepository extends JpaRepository<ResourcePropertyDetail, Long> {
    public List<ResourcePropertyDetail> getAllByPropertyId(Long id);
}
