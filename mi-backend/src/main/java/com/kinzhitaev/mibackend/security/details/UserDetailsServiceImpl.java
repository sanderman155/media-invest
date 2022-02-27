package com.kinzhitaev.mibackend.security.details;

import com.kinzhitaev.mibackend.security.model.User;
import com.kinzhitaev.mibackend.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow( () ->
                new UsernameNotFoundException("Username " + username + " not found"));
        return UserDetailsImpl.build(user);
    }

    public UserDetails loadUserByUsernameOrEmail(String login) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(login, login).orElseThrow(() ->
                new UsernameNotFoundException("Username " + login + " not found"));
        return UserDetailsImpl.build(user);
    }
}
