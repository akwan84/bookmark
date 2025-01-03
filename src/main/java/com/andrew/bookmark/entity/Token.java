package com.andrew.bookmark.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Entity to represent a token in the database represented by a randomly generated UUID
 */
@Entity
public class Token {
    private static final int LENGTH = 15;

    @Id
    @GeneratedValue
    private Integer id;

    private String token;

    private LocalDateTime expirationTime;

    /** Foreign key from the app_user table */
    @OneToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    /**
     * Constructor to generate a new token
     */
    public Token(){
        this.token = UUID.randomUUID().toString();

        ZonedDateTime utcExpTime = ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(Token.LENGTH);
        this.expirationTime = utcExpTime.toLocalDateTime();
    }

    /**
     * Getter for the token ID
     * @return token ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter for the token ID
     * @param id ID to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for the token string
     * @return token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for the token string
     * @param token token string to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter for the associated user
     * @return user associated with the token
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for the associated user
     * @param user user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter for the token expiration time
     * @return expiration time of the token
     */
    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    /**
     * Setter for the token expiration time
     * @param expirationTime expiration time to set
     */
    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
