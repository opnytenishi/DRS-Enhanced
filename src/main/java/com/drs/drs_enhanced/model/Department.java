package com.drs.drs_enhanced.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department extends User{

    private String departmentName;
    
    @ManyToMany
    @JoinTable(
        name = "department_supply",
        joinColumns = @JoinColumn(name = "department_id"),
        inverseJoinColumns = @JoinColumn(name = "supply_id")
    )
    private List<Supply> supplies;
    
    public Department() {
    }

    public Department(String departmentName, List<Supply> supplies, String name, String email, String password,String region) {
        super(name, email, password, region);
        this.departmentName = departmentName;
        this.supplies = supplies;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;
    }

    @Override
    public String toString() {
        return "Department{" + "departmentName=" + departmentName + 
                ", supplies=" + supplies + '}';
    }
    
}
