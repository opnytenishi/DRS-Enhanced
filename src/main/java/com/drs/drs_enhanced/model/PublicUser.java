package com.drs.drs_enhanced.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "public_user")
public class PublicUser extends User {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String address;

    public PublicUser() {
    }

    public PublicUser(String name, String email, String password,
            String region, String address) {
        super(name, email, password, region);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
