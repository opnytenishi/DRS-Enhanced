package com.drs.drs_enhanced.controller;

public enum UserType {
    PUBLIC_USER("Public User"),
    RESPONDER("Responder"), 
    OTHER_DEPARTMENT("Other Department");

    
    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
    
}
