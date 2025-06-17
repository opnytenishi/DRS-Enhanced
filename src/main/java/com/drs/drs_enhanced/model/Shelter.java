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
@Table(name = "shelters")
@NamedQueries({
    @NamedQuery(
            name = "Shelter.findAll",
            query = "SELECT s FROM Shelter s WHERE s.region = :region"
    )
})
public class Shelter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String shelterDetail;
    private String region;

    public Shelter() {
    }

    public Shelter(String shelterDetail, String region) {
        this.shelterDetail = shelterDetail;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShelterDetail() {
        return shelterDetail;
    }

    public void setShelterDetail(String shelterDetail) {
        this.shelterDetail = shelterDetail;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return getShelterDetail();
    }

}
