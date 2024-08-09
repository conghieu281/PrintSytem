package com.example.printsystem.models.entity;

import com.example.printsystem.models.Enum.EDeliveryStatus;
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
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, insertable = false)
    private Long shippingMethodId;

    @Column(insertable = false, updatable = false)
    private Long customerId;

    private Long deliverId;

    @Column(insertable = false, updatable = false)
    private Long projectId;

    private String deliveryAddress;
    private LocalDateTime estimateDeliveryTime;
    private LocalDateTime actualDeliveryTime;

    @Enumerated(EnumType.STRING)
    private EDeliveryStatus deliveryStatus;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_delivery_shipping"))
    private ShippingMethod shipDelivery;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_delivery_customer"))
    private Customer customerDelivery;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_delivery_project"))
    private Project projectDelivery;

    public Delivery(Long id) {
        this.id = id;
    }
}
