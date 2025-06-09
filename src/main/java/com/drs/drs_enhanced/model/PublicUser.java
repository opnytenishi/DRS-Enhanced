package com.drs.drs_enhanced.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "public_user")
public class PublicUser extends User{

    public PublicUser() {
    }

    public PublicUser(String name, String email, String password, String region) {
        super(name, email, password, region);
    }
    
}
