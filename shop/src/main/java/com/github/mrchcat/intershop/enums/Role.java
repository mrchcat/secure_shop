package com.github.mrchcat.intershop.enums;

public enum Role {
    ADMIN("ADMIN"), USER("USER");

    public final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }
}
