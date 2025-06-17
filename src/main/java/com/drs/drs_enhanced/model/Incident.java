package com.drs.drs_enhanced.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "incidents")
@NamedQueries({
    @NamedQuery(
        name = "Incident.findUnassigned",
        query = "SELECT i FROM Incident i WHERE i.assignedDepartment IS NULL"
    ),
    @NamedQuery(
    name = "Incident.findByDepartmentId",
    query = "SELECT i FROM Incident i WHERE i.assignedDepartment.userId = :deptId"
)
})
public class Incident implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String incidentType;
    private String description;
    private int priorityLevel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department assignedDepartment;

    public Incident() {
    }

    public Incident(String incidentType, String description, int priorityLevel, User user, Department assignedDepartment) {
        this.incidentType = incidentType;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.user = user;
        this.assignedDepartment = assignedDepartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Department getAssignedDepartment() {
        return assignedDepartment;
    }

    public void setAssignedDepartment(Department assignedDepartment) {
        this.assignedDepartment = assignedDepartment;
    }

    @Override
    public String toString() {
        return "Incident{" + "id=" + id + ", incidentType=" + incidentType + 
                ", description=" + description + ", priorityLevel=" + 
                priorityLevel + ", user=" + user + ", assignedDepartment=" + 
                assignedDepartment + '}';
    }
    
}
