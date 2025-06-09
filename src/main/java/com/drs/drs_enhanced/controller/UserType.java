package com.drs.drs_enhanced.controller;

public enum UserType {
    PUBLIC_USER("Public User"), 
    GUEST_USER("Guest User"), 
    RESPONDER("Responder"), 
    OTHER_DEPARTMENT("Fire Station"), 
    OTHER_DEPARTMENT_2("Mobile Hospital"), 
    OTHER_DEPARTMENT_3("Rescue Team");
    
    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
    
}
