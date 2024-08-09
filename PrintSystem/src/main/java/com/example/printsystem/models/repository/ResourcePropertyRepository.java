package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.ResourceProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourcePropertyRepository extends JpaRepository<ResourceProperty, Long> {
}
