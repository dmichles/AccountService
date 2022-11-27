package com.example.account.models.entities.constants;

public enum UserRole {
    USER("USER"),
    ACCOUNTANT("ACCOUNTANT"),
    ADMINISTRATOR("ADMINISTRATOR"),
    AUDITOR("AUDITOR");

    private final String role;

    UserRole(final String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public boolean isFromAdminGroup(){
        return this == ADMINISTRATOR;
    }
}
