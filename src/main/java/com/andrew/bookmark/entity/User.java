package com.andrew.bookmark.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * Entity to represent a user
 */
@Entity
@Table(name="app_user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    /** Access token mapped to the user */
    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private Token token;

    /** List of bookmarks created by the user */
    @OneToMany(
            mappedBy = "user"
    )
    @JsonManagedReference
    private List<URL> urls;

    public User(){}

    /**
     * Constructor
     * @param username username of the user
     * @param password password of the user
     */
    public User(
            String username,
            String password
    ) {
        this.username = username;
        this.password = password;
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
     * Getter for the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username
     * @param username username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password
     * @param password password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the bookmarked URLs created by the user
     * @return bookmarked URLs
     */
    public List<URL> getUrls() {
        return urls;
    }

    /**
     * Setter for the bookmarked URLs created by the user
     * @param urls bookmarked URLs
     */
    public void setUrls(List<URL> urls) {
        this.urls = urls;
    }

    /**
     * Getter for the access token associated with the user
     * @return access token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Setter for the access token associated with the user
     * @param token access token
     */
    public void setToken(Token token) {
        this.token = token;
    }
}
