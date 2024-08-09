package com.example.printsystem.security;

import com.example.printsystem.models.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String userName;
    @JsonIgnore
    private String password;
    private String fullName;
    private LocalDate dateOfBirth;
    private String avatar;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String phoneNumber;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
    }

    public static CustomUserDetails mapUserToUserDetail(User user){
        Collection<GrantedAuthority> grantedAuthorities = user.getPermissions().stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.getRolePermission().getRoleName().name()))
                .collect(Collectors.toList());
        return new CustomUserDetails(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getFullName(),
                user.getDateOfBirth(),
                user.getAvatar(),
                user.getEmail(),
                user.getCreateTime(),
                user.getUpdateTime(),
                user.getPhoneNumber(),
                grantedAuthorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
