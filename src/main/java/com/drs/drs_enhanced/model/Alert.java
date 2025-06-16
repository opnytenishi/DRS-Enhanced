package com.drs.drs_enhanced.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "alert")
@NamedQueries({
    @NamedQuery(
            name = "Alert.findByRegion",
            query = "SELECT a FROM Alert a WHERE a.alertRegion = :region"
    ),
    @NamedQuery(
            name = "Alert.findAll",
            query = "SELECT a FROM Alert a"
    )
})
public class Alert implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String alertRegion;

    public Alert() {
    }

    public Alert(String alertRegion) {
        this.alertRegion = alertRegion;
    }

    public String getAlertRegion() {
        return alertRegion;
    }

    public void setAlertRegion(String alertRegion) {
        this.alertRegion = alertRegion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getAlertRegion();
    }
}
