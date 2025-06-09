package com.drs.drs_enhanced.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "publics")
public class Supply{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;

    @ManyToMany(mappedBy = "supplies")
    private List<Department> departments;
    
    public Supply() {
    }

    public Supply(Long id, String name, int quantity, List<Department> departments) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.departments = departments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "Supply{" + "id=" + id + ", name=" + name + ", quantity=" + quantity + ", departments=" + departments + '}';
    }

}
