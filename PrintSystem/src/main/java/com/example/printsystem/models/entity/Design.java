package com.example.printsystem.models.entity;

import com.example.printsystem.models.Enum.EBillStatus;
import com.example.printsystem.models.Enum.EDesignStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Design {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private Long projectId;

    @Column(insertable = false, updatable = false)
    private Long designerId;

    private String filePath;
    private LocalDateTime designTime;

    @Enumerated(EnumType.STRING)
    private EDesignStatus designStatus;

    private Long approveId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "designPrintJob", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PrintJobs> printJobList;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_design_user"))
    private User userDesign;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_design_project"))
    private Project projectDesign;

    public Design(Long id) {
        this.id = id;
    }
}
