package com.kinzhitaev.mibackend.security.dto;

import lombok.Data;

@Data
public class SignInDto {
    private String login;
    private String password;
}
