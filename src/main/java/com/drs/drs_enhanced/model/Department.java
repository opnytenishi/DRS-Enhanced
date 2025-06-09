/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.drs.drs_enhanced.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "otherDepartments")
public class Department extends User {

    private String departmentName;
    private List<String> assignedTasks;
    private List<String> resources;

    public Department(String userId, String name, String email, String password, String departmentName, String assignedRegion) {
        super(userId, name, email, password, "OtherDepartment", assignedRegion);
        this.departmentName = departmentName;
        this.assignedTasks = new ArrayList<>();
        this.resources = new ArrayList<>();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void assignTask(String task) {
        assignedTasks.add(task);
    }

    public void addResource(String resource) {
        resources.add(resource);
    }

    @Override
    public String toString() {
        return "OtherDepartment [departmentName=" + departmentName + ", tasks=" + assignedTasks + ", resources=" + resources + "]";
    }
}
