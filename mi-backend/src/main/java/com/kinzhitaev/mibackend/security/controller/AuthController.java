package com.kinzhitaev.mibackend.security.controller;

import com.kinzhitaev.mibackend.security.config.jwt.JwtUtils;
import com.kinzhitaev.mibackend.security.details.UserDetailsImpl;
import com.kinzhitaev.mibackend.security.dto.JwtResponse;
import com.kinzhitaev.mibackend.security.dto.MessageResponse;
import com.kinzhitaev.mibackend.security.dto.RegisterDTO;
import com.kinzhitaev.mibackend.security.dto.SignInDto;
import com.kinzhitaev.mibackend.security.repository.UserRepository;
import com.kinzhitaev.mibackend.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is exists"));
        }
        if (userRepository.existsByUsername(registerDto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is exists"));
        }

        authService.saveUserFromRegisterDto(registerDto);
        return ResponseEntity.ok().body(new MessageResponse("User created"));
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> authUser(@RequestBody SignInDto signInDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getLogin(),
                        signInDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(userDetails.getId(),
                token,
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getRole().name()));
    }
}
