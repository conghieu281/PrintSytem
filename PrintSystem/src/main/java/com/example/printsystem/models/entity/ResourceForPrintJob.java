package com.example.printsystem.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceForPrintJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private Long resourcePropertyDetailId;

    @Column(insertable = false, updatable = false)
    private Long printJobId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_print_propertyDetail"))
    private ResourcePropertyDetail resourceDetailPrint;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_resource_printJob"))
    private PrintJobs printJobs;

    public ResourceForPrintJob(Long id) {
        this.id = id;
    }
}
