package com.drs.drs_enhanced.controller;

public enum IncidentType {
    EARTHQUAKE("Earthquake", 1),
    CYCLONE("Cyclone", 2),
    FLOOD("Flood", 3),
    FIRE("Fire", 4), 
    MEDICAL_EMERGENCY("Medical Emergency", 5),
    LANDSLIDE("Landslide", 6), 
    ACCIDENT("Accident", 7),
    MISSING_PERSON("Missing Person", 8);
    
    private final String displayName;
    private final int priority;

    IncidentType(String displayName, int priority) {
        this.displayName = displayName;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getPriority() {
        return priority;
    } 
    
}
