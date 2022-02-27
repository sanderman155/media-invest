package com.kinzhitaev.mibackend.security.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ERole {
    USER(Set.of(Permissions.READ)),
    ADMIN(Set.of(Permissions.READ, Permissions.WRITE));

    Set<Permissions> permissionsSet;

    ERole(Set<Permissions> permissionsSet) {
        this.permissionsSet = permissionsSet;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return permissionsSet.stream().map(
                permission -> new SimpleGrantedAuthority(permission.getPermission())
        ).collect(Collectors.toSet());
    }
}
