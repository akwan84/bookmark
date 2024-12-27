package com.andrew.bookmark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class URL {
    @Id
    @GeneratedValue
    private Integer id;

    private String fullUrl;

    @Column(unique = true)
    private String shortCode;

    private int type; //1 - normal, 2 - temporary, 3 - one-time

    //expiration time for temporary links
    private LocalDateTime expirationTime;

    //boolean to check if one-time links are still active
    private boolean active;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    @JsonBackReference
    private User user;

    public URL() {}

    public URL(String fullUrl, String shortCode, int type, int length) {
        this.fullUrl = fullUrl;
        this.shortCode = shortCode;
        this.type = type;

        if(type == 2) {
            this.expirationTime = LocalDateTime.now().plus(length, ChronoUnit.MINUTES);
        } else if(type == 3) {
            this.active = true;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
