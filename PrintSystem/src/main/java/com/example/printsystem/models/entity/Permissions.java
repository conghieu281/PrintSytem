package com.example.printsystem.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private Long userId;

    @Column(insertable = false, updatable = false)
    private Long roleId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_permission_user"))
    @JsonIgnore
    private User userPermission;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_permission_role"))
    @JsonIgnore
    private Role rolePermission;

    public Permissions(Long id) {
        this.id = id;
    }
}
