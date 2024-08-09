package com.example.printsystem.models.entity;

import com.example.printsystem.models.Enum.ERoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleCode;

    @Enumerated(EnumType.STRING)
    private ERoles roleName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "", cascade = CascadeType.ALL)
    @JsonIgnore
    private  List<Permissions> permissionsList;

    public Role(Long id) {
        this.id = id;
    }
}
