package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByProjectId(Long projectId);
}
