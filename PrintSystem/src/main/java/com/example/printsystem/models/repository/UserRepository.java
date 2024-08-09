package com.example.printsystem.models.repository;

import com.example.printsystem.models.Enum.ERoles;
import com.example.printsystem.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName (String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    @Query("SELECT u FROM User u JOIN u.permissions p JOIN p.rolePermission r WHERE r.roleName = :roleName")
    List<User> findUsersByRoleName(@Param("roleName") ERoles roleName);
}
