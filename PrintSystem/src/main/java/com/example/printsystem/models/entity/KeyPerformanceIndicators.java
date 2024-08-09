package com.example.printsystem.models.entity;

import com.example.printsystem.models.Enum.EKpiPeriod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class KeyPerformanceIndicators {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, insertable = false)
    private Long employeeId;

    private String indicatorName;
    private Integer target;
    private Integer actuallyAchieved;
    @Enumerated(EnumType.STRING)
    private EKpiPeriod period;
    private Boolean achieveKPI;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_kpi_user"))
    private User userKPI;

    public KeyPerformanceIndicators(Long id) {
        this.id = id;
    }
}
