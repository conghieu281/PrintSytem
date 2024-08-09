package com.example.printsystem.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
