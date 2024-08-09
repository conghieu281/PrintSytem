package com.example.printsystem.controllers;

import com.example.printsystem.models.dto.*;
import com.example.printsystem.models.entity.User;
import com.example.printsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService _userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        User user = _userService.registerUser(userDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        String token = _userService.loginUser(loginDTO);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody EmailDTO emailDTO) {
        _userService.sendResetPasswordEmail(emailDTO.getEmail());
        return ResponseEntity.ok("Reset password email sent.");
    }

    @PostMapping("/confirm-reset-password")
    public ResponseEntity<?> confirmResetPassword(@RequestBody ConfirmResetPasswordDTO confirmResetPasswordDTO) {
        _userService.confirmResetPasswordCode(
                confirmResetPasswordDTO.getEmail(),
                confirmResetPasswordDTO.getConfirmationCode(),
                confirmResetPasswordDTO.getNewPassword()
        );
        return ResponseEntity.ok("Password reset successful.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        _userService.changePassword(changePasswordDTO.getUserId(), changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        return ResponseEntity.ok("Password changed successfully.");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/user")
    public List<User> getAllUsers() {
        return _userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = _userService.getUserById(id).get();
        return ResponseEntity.ok().body(user);
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/user/create")
    public ResponseEntity<?> CreateUser(@RequestBody UserDTO userDTO) {
        User user = _userService.createUser(userDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("user/deliver")
    public List<User> getUsersWithDeliveryRole() {
        return _userService.getUsersWithDeliveryRole();
    }
}
