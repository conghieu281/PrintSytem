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
public class ImportCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalMoney;

    @Column(updatable = false, insertable = false)
    private Long resourcePropertyDetailId;

    @Column(updatable = false, insertable = false)
    private Long employeeId;

    private String tradingCode;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_coupon_user"))
    private User userCoupon;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_coupon_resourceDetail"))
    private ResourcePropertyDetail resourceDetailCoupon;

    public ImportCoupon(Long id) {
        this.id = id;
    }
}
