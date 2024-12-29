package com.andrew.bookmark.repository;

import com.andrew.bookmark.entity.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository for Token entities
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {
    /**
     * Query to find a token by user ID
     * @param userId user ID
     * @return Token entity if it exists
     */
    @Query("SELECT t FROM User u JOIN u.token t WHERE u.id = :userId")
    Optional<Token> findUserWithTokenById(@Param("userId") Integer userId);

    /**
     * Query to find a token entity given a token string
     * @param token token string
     * @return Token entity if it exists
     */
    Optional<Token> findByToken(String token);

    /**
     * Query to delete a token entity given a token string
     * @param token token string
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM token WHERE token = :token", nativeQuery = true)
    void deleteToken(@Param("token") String token);

}
