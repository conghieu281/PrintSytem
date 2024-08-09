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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customerBill", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bill> billList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customerDelivery", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Delivery> deliveryList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customerFeedback", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CustomerFeedback> feedbackList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customerProject", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> projectList;

    public Customer(Long id) {
        this.id = id;
    }
}

