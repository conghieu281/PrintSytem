package com.example.printsystem.models.entity;

import com.example.printsystem.models.Enum.EProjectStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private String requestDescriptionFromCustomer;
    private LocalDateTime startDate;

    @Column(insertable = false, updatable = false)
    private Long employeeId;
    private LocalDateTime expectedEndDate;

    @Column(insertable = false, updatable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    private EProjectStatus projectStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "projectDelivery", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Delivery> deliveryList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "projectDesign", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Design> designList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "projectFeedback", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CustomerFeedback> feedbackList;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_project_customer"))
    private Customer customerProject;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_project_user"))
    private User userProject;

    public Project(Long id) {
        this.id = id;
    }
}
