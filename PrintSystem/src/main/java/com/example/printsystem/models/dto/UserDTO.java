package com.example.printsystem.models.dto;

import com.example.printsystem.models.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private String userName;
    private String password;
    private String fullName;
    private LocalDate dateOfBirth;
    private String avatar;
    private String email;
    private String phoneNumber;
    private String role;
}
