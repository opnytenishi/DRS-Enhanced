/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.drs.drs_enhanced.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Entity
@Table(name = "responders")
public class Responder extends User {

    private String department;
    private String assignedRegion;
    private List<String> supplies;
    private List<String> alerts;
    private List<String> tasks;
    private PriorityQueue<Disaster> incidentQueue;

    public Responder(String userId, String name, String email, String password, String department, String assignedRegion) {
        super(userId, name, email, password, "Responder", assignedRegion);
        this.department = department;
        this.assignedRegion = assignedRegion;
        this.supplies = new ArrayList<>();
        this.alerts = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.incidentQueue = new PriorityQueue<>(Comparator.comparingInt(Disaster::getPriorityLevel).reversed());
    }

    public void addSupply(String item) {
        supplies.add(item);
    }

    public boolean removeSupply(String item) {
        return supplies.remove(item);
    }

    public void sendAlert(String region) {
        alerts.add(region);
    }

    public boolean removeAlert(String region) {
        return alerts.remove(region);
    }

    public void assignTask(String task) {
        tasks.add(task);
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void addIncident(Disaster incident) {
        incidentQueue.add(incident);
    }

    public Disaster handleNextIncident() {
        return incidentQueue.poll();
    }

}
