package com.example.printsystem.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private Long projectId;

    @Column(insertable = false, updatable = false)
    private Long customerId;

    private String feedbackContent;
    private String responseByCompany;

    @Column(insertable = false, updatable = false)
    private Long userId;

    private LocalDateTime feedbackTime;
    private LocalDateTime responseTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_feedback_user"))
    private User userFeedback;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_feedback_project"))
    private Project projectFeedback;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_feedback_customer"))
    private Customer customerFeedback;

    public CustomerFeedback(Long id) {
        this.id = id;
    }
}
