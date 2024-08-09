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
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    private Long userId;

    private String content;
    private String link;
    private LocalDateTime createTime;
    private Boolean isSeen;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_notification_user"))
    @JsonIgnore
    private User userNotification;

    public Notification(Long id) {
        this.id = id;
    }
}
