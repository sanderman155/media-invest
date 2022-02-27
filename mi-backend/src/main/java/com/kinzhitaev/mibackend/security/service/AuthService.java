package com.kinzhitaev.mibackend.security.service;

import com.kinzhitaev.mibackend.security.dto.RegisterDTO;
import com.kinzhitaev.mibackend.security.enums.ERole;
import com.kinzhitaev.mibackend.security.model.User;
import com.kinzhitaev.mibackend.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void saveUserFromRegisterDto(RegisterDTO registerDTO) {
        User user = new User(registerDTO.getUsername(),
                registerDTO.getEmail(),
                passwordEncoder.encode(registerDTO.getPassword()),
                registerDTO.getRole() != null? registerDTO.getRole() : ERole.USER);
        userRepository.save(user);
    }
}
