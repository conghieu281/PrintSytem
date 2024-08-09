package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resources, Long> {
}
