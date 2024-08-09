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
public class ResourcePropertyDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private Long propertyId;

    private String propertyDetailName;
    private String image;
    private Double price;
    private Integer quantity;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resourceDetailCoupon", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ImportCoupon> importCoupons;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_detail_property"))
    private ResourceProperty propertyDetail;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resourceDetailPrint", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ResourceForPrintJob> printJobList;

    public ResourcePropertyDetail(Long id) {
        this.id = id;
    }
}
