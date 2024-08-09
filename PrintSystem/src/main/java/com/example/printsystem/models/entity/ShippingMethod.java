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
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shippingMethodName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shipDelivery", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Delivery> deliveries;

    public ShippingMethod(Long id) {
        this.id = id;
    }
}
