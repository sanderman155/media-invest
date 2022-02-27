package com.kinzhitaev.mibackend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private Long id;
    private String token;
    private String username;
    private String email;
    private String roleName;

}
