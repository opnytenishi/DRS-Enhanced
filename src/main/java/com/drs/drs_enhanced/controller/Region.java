package com.drs.drs_enhanced.controller;

public enum Region {
    NORTH("North"), 
    SOUTH("South"), 
    EAST("East"), 
    WEST("West"),
    CENTRAL("Central");

    
    private final String displayName;

    private Region(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
