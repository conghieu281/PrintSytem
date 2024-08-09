package com.example.printsystem.models.entity;

import com.example.printsystem.models.Enum.EResourceStatus;
import com.example.printsystem.models.Enum.EResourceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resources {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String resourceName;
    private String image;

    @Enumerated(EnumType.STRING)
    private EResourceType resourceType;

    private Integer availableQuantity;

    @Enumerated(EnumType.STRING)
    private EResourceStatus resourceStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resourceProperty", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ResourceProperty> propertyList;

    public Resources(Long id) {
        this.id = id;
    }
}
