package com.andrew.bookmark.repository;

import com.andrew.bookmark.entity.Token;
import com.andrew.bookmark.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("SELECT t FROM User u JOIN u.token t WHERE u.id = :userId")
    Optional<Token> findUserWithTokenById(@Param("userId") Integer userId);

    Optional<Token> findByToken(String token);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM token WHERE token = :token", nativeQuery = true)
    void deleteToken(@Param("token") String token);

}
