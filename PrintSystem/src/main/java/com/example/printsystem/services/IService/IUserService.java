package com.example.printsystem.services.IService;

import com.example.printsystem.models.dto.LoginDTO;
import com.example.printsystem.models.dto.UserDTO;
import com.example.printsystem.models.entity.User;

import java.util.List;

public interface IUserService {
    User createUser(UserDTO userDTO);
    public User registerUser(UserDTO userDTO);
    public String loginUser(LoginDTO loginDTO);
    public void sendResetPasswordEmail(String email);
    public void confirmResetPasswordCode(String email, String confirmationCode, String newPassword);
    public void changePassword(Long userId, String oldPassword, String newPassword);
    public List<User> getUsersWithDeliveryRole();

}
