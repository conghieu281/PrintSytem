package com.example.printsystem.models.repository;

import com.example.printsystem.models.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions, Long> {
}
