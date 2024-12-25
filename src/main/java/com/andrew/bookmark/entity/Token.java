package com.andrew.bookmark.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Token {
    @Id
    @GeneratedValue
    private Integer id;

    private String token;

    //TODO: Add an expiration date to the tokens

    @OneToOne
    @JoinColumn(
            name = "user_id" //foreign key
    )
    private User user;

    public Token(){
        this.token = UUID.randomUUID().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
