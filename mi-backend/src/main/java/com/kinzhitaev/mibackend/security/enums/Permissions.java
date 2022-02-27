package com.kinzhitaev.mibackend.security.enums;

public enum Permissions {
    READ("READ"),
    WRITE("WRITE");

    final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
