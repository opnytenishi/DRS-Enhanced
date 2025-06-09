/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.drs.drs_enhanced.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "publics")
public class Public extends User implements Serializable{

    // Constructor
    public Public(String userId, String name, String email, String password, String region) {
        super(userId, name, email, password, "PublicUser", region);
    }

    /**
     * Creates a new help request (incident report) using the current user's info.
     *
     * @param type        The type of the incident.
     * @param description Description of the incident.
     * @param priority    Priority level (e.g., 1 = High, 2 = Medium, 3 = Low).
     * @return A new Disaster object.
     */
    public Disaster createHelpRequest(String type, String description, int priority) {
        return new Disaster(type, description, this.getRegion(), this.getUserId(), priority);
    }

    @Override
    public String toString() {
        return "PublicUser{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", region='" + getRegion() + '\'' +
                '}';
    }
}
