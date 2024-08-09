package com.example.printsystem.models.entity;

import com.example.printsystem.models.Enum.EBillStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String billName;

    @Enumerated(EnumType.STRING)
    private EBillStatus billStatus;

    private Double totalMoney;
    private Long projectId;

    @Column(updatable = false, insertable = false)
    private Long customerId;

    private String tradingCode;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @Column(updatable = false, insertable = false)
    private Long employeeId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_bill_user"))
    private User userBill;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_bill_customer"))
    private Customer customerBill;

    public Bill(Long id) {
        this.id = id;
    }
}
