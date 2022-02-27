package com.kinzhitaev.mibackend.security.dto;

import com.kinzhitaev.mibackend.security.enums.ERole;
import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String email;
    private String password;
    private ERole role;
}
