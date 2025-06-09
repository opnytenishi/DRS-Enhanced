package com.drs.drs_enhanced.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "responders")
public class Responder extends User{

    public Responder() {
    }
    
    
}
