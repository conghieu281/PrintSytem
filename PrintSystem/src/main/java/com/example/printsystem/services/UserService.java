package com.example.printsystem.services;

import com.example.printsystem.models.Enum.ERoles;
import com.example.printsystem.models.dto.LoginDTO;
import com.example.printsystem.models.dto.UserDTO;
import com.example.printsystem.models.entity.Permissions;
import com.example.printsystem.models.entity.Role;
import com.example.printsystem.models.entity.User;
import com.example.printsystem.models.repository.PermissionRepository;
import com.example.printsystem.models.repository.RoleRepository;
import com.example.printsystem.models.repository.UserRepository;
import com.example.printsystem.security.CustomUserDetails;
import com.example.printsystem.services.IService.IUserService;
import com.example.printsystem.util.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private RoleRepository _roleRepository;

    @Autowired
    private PermissionRepository _permissionRepository;

    @Autowired
    private PasswordEncoder _passwordEncoder;

    @Autowired
    private EmailService _emailService;

    @Autowired
    private JwtTokenProvider _jwtUtils;

    private final ConcurrentMap<String, String> confirmationCodes = new ConcurrentHashMap<>();

    @Override
    public User registerUser(UserDTO userDTO) {
        if (_userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        if (_userRepository.existsByUserName(userDTO.getUserName())) {
            throw new RuntimeException("Username already exists.");
        }

        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(_passwordEncoder.encode(userDTO.getPassword()));
        user.setFullName(userDTO.getFullName());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setAvatar(userDTO.getAvatar());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setCreateTime(LocalDateTime.now());

        Role employeeRole = _roleRepository.findByRoleName(ERoles.ROLE_EMPLOYEE);
        if (employeeRole == null) {
            throw new RuntimeException("Employee role not found!");
        }
        User newUser = _userRepository.save(user);
        // Create permission entry
        Permissions permissions = new Permissions();
        permissions.setUserPermission(newUser);
        permissions.setRolePermission(employeeRole);
        _permissionRepository.save(permissions);

        return newUser;
    }

    @Override
    public String loginUser(LoginDTO loginDTO) {
        Optional<User> userOpt = _userRepository.findByUserName(loginDTO.getUserName());
        if (userOpt.isEmpty() || !_passwordEncoder.matches(loginDTO.getPassword(), userOpt.get().getPassword())) {
            throw new RuntimeException("Invalid username or password.");
        }
        CustomUserDetails userDetails = CustomUserDetails.mapUserToUserDetail(userOpt.get());
        return _jwtUtils.generateToken(userDetails);
    }

    @Override
    public void sendResetPasswordEmail(String email) {
        Optional<User> userOpt = _userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email not found.");
        }
        String confirmationCode = _emailService.generateConfirmationCode();
        confirmationCodes.put(email, confirmationCode);
        _emailService.sendResetPasswordEmail(email, confirmationCode);
    }

    @Override
    public void confirmResetPasswordCode(String email, String confirmationCode, String newPassword) {
        String storedCode = confirmationCodes.get(email);
        if (storedCode == null || !storedCode.equals(confirmationCode)) {
            throw new RuntimeException("Invalid or expired confirmation code.");
        }

        User user = _userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found."));
        user.setPassword(_passwordEncoder.encode(newPassword));
        user.setUpdateTime(LocalDateTime.now());
        _userRepository.save(user);
        confirmationCodes.remove(email);
    }

    @Override
    public User createUser(UserDTO userDTO) {
        if (_userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        if (_userRepository.existsByUserName(userDTO.getUserName())) {
            throw new RuntimeException("Username already exists.");
        }

        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(_passwordEncoder.encode(userDTO.getPassword()));
        user.setFullName(userDTO.getFullName());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setAvatar(userDTO.getAvatar());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setCreateTime(LocalDateTime.now());


        Role employeeRole = _roleRepository.findByRoleName(ERoles.ROLE_EMPLOYEE);
        if (employeeRole == null) {
            throw new RuntimeException("Employee role not found!");
        }
        User newUser = _userRepository.save(user);
        // Create permission entry
        Permissions permissions = new Permissions();
        permissions.setUserPermission(newUser);
        permissions.setRolePermission(employeeRole);
        _permissionRepository.save(permissions);

        return newUser;
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = _userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
        if (!_passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect.");
        }

        user.setPassword(_passwordEncoder.encode(newPassword));
        user.setUpdateTime(LocalDateTime.now());
        _userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return _userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return _userRepository.findById(id);
    }

    @Override
    public List<User> getUsersWithDeliveryRole() {
        return _userRepository.findUsersByRoleName(ERoles.ROLE_DELIVERY);
    }
}
