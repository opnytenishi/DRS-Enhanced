package com.drs.drs_enhanced.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    protected String userId;
    protected String name;
    protected String email;
    protected String password;
    protected String userType;
    protected String region;

    public User(String userId, String name, String email, String password, String userType, String region) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.region = region;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", userType=" + userType + ", Region=" + region + "]";
    }

}
