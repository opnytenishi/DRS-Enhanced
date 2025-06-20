package com.drs.drs_enhanced.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "responders")
public class Responder extends User {

    private static final long serialVersionUID = 1L;

    public Responder() {
    }

    public Responder(String name, String email, String password, String region) {
        super(name, email, password, region);
    }

}
