package com.driver.model;

import javax.persistence.*;

@Entity
public class Admin{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;

    @Column(unique = true,nullable = false)
    private String username;
    private String password;



    // No args Constructor
    public Admin() {
    }

    // All Args Constructor
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter and Setter
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}