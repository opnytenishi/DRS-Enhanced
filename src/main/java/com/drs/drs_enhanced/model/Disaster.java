package com.drs.drs_enhanced.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "disasters")
public class Disaster {

    private String incidentType;
    private String description;
    private String region;
    private String userId;
    private int priorityLevel;

    public Disaster(String type, String description, String region, String userId, int priorityLevel) {
        this.incidentType = type;
        this.description = description;
        this.region = region;
        this.userId = userId;
        this.priorityLevel = priorityLevel;
        // Add timestamp or status if needed
    }

    public String getIncidentType() {
        return incidentType;
    }

    public String getDescription() {
        return description;
    }

    public String getRegion() {
        return region;
    }

    public String getUserId() {
        return userId;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    
    @Override
    public String toString() {
        return "IncidentReport [type=" + incidentType + ", region=" + region + ", priority=" + priorityLevel + "]";
    }
}
