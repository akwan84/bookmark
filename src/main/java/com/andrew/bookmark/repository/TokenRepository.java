package com.andrew.bookmark.repository;

import com.andrew.bookmark.entity.Token;
import com.andrew.bookmark.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("SELECT t FROM User u JOIN u.token t WHERE u.id = :userId")
    Optional<Token> findUserWithTokenById(@Param("userId") Integer userId);
}
