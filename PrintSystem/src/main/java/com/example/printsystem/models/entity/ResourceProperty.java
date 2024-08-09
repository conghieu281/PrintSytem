package com.example.printsystem.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class ResourceProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String resourcePropertyName;

    @Column(updatable = false, insertable = false)
    private Long resourceId;

    private Integer quantity;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_property_resource"))
    private Resources resourceProperty;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "propertyDetail", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ResourcePropertyDetail> propertyDetails;

    public ResourceProperty(Long id) {
        this.id = id;
    }
}
