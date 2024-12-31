package com.andrew.bookmark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Entity to represent a bookmarked URL
 */
@Entity
public class URL {
    @Id
    @GeneratedValue
    private Integer id;

    private String fullUrl;

    @Column(unique = true)
    private String shortCode;

    private int numVisits;

    /** 1 - normal, 2 - temporary, 3 - one-time */
    private int type;

    /** Expiration time for temporary links */
    private LocalDateTime expirationTime;

    /** Boolean to check if one-time links are still active */
    private boolean active;

    /** User associated with the bookmarked URL */
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    @JsonBackReference
    private User user;

    public URL() {}

    /**
     * Constructor for a bookmarked URL entity
     * @param fullUrl URL to be bookmarked
     * @param shortCode Short code mapped to the bookmarked URL
     * @param type Type of bookmark being made (normal, temporary, one-time)
     * @param length For temporary links, number of minutes the link will be active for
     * @param active For one-time links, whether or not they are active
     */
    public URL(String fullUrl, String shortCode, int type, int length, boolean active) {
        this.fullUrl = fullUrl;
        this.shortCode = shortCode;
        this.type = type;

        if(type == 2) { //set expiration time for temporary links
            this.expirationTime = LocalDateTime.now().plus(length, ChronoUnit.MINUTES);
        } else if(type == 3) { //set link as active for one-time links
            this.active = active;
        }
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
     * Getter for the bookmarked URL
     * @return bookmarked URL
     */
    public String getFullUrl() {
        return fullUrl;
    }

    /**
     * Setter for the bookmarked URL
     * @param fullUrl URL to bookmark
     */
    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    /**
     * Getter for the code associated with the bookmark
     * @return code associated with the bookmark
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     * Setter for the code associated with the bookmark
     * @param shortCode Code to map the URL to
     */
    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    /**
     * Getter for the bookmark type
     * @return bookmark type
     */
    public int getType() {
        return type;
    }

    /**
     * Setter for the bookmark type
     * @param type bookmark type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Getter for the bookmark expiration time
     * @return expiration time for the bookmark
     */
    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    /**
     * Setter for the bookmark expiration time
     * @param expirationTime new expiration time
     */
    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * Getter for one-time link validity
     * @return true if one-time link is valid, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Setter for one-time link validity
     * @param active true for valid one-time link, false otherwise
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Getter for the associated user
     * @return associated user
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for the associated user
     * @param user assiciated user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter for the number of visits
     * @return number of visits
     */
    public int getNumVisits() {
        return numVisits;
    }

    /**
     * Setter for the number of visits
     * @param numVisits number of visits
     */
    public void setNumVisits(int numVisits) {
        this.numVisits = numVisits;
    }
}
