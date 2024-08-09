package com.example.printsystem.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer numberOfMember;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long managerId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "team", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> users;

    public Team(Long id) {
        this.id = id;
    }
}
