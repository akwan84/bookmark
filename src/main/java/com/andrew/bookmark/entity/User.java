package com.andrew.bookmark.entity;

import jakarta.persistence.*;

@Entity
@Table(name="app_user")
public class User {

    @Id
    @GeneratedValue
    Integer id;

    @Column(unique = true)
    String username;

    String password;

    public User(){}

    public User(
            String username,
            String password
    ) {
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
