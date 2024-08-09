package com.example.printsystem.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String fullName;
    private LocalDate dateOfBirth;
    private String avatar;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String phoneNumber;

    @Column(insertable = false, updatable = false, name = "teamId")
    private Long teamId;

    private Boolean isActive;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userNotification", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notification> notificationList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userBill", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bill> billList;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "teamId", foreignKey = @ForeignKey(name = "fk_user_team"))
    private Team team;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userCoupon", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ImportCoupon> importCoupons;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userKPI", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<KeyPerformanceIndicators> keyPerformanceIndicators;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userPermission", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Permissions> permissions;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userDesign", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Design> designList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userFeedback", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CustomerFeedback> feedbackList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userProject", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> projectList;

    public User(Long id) {
        this.id = id;
    }
}
