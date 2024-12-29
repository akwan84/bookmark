package com.andrew.bookmark.repository;

import com.andrew.bookmark.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository for User entities
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Query to find a user by username
     * @param username username
     * @return User entity if it exists
     */
    Optional<User> findByUsername(String username);

    /**
     * Query to find a user by an associated access token
     * @param token access token
     * @return User entity if it exists
     */
    @Query("SELECT u FROM User u JOIN u.token t WHERE t.token = :token")
    Optional<User> findUserWithToken(@Param("token") String token);
}
