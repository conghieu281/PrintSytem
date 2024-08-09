package com.example.printsystem.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmResetPasswordDTO {
    private String email;
    private String confirmationCode;
    private String newPassword;
}
