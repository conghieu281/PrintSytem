package com.example.printsystem.models.repository;

import com.example.printsystem.models.Enum.ERoles;
import com.example.printsystem.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByRoleName(ERoles roleName);
}
