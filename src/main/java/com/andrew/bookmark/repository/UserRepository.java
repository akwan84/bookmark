package com.andrew.bookmark.repository;

import com.andrew.bookmark.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.token t WHERE t.token = :token")
    Optional<User> findUserWithToken(@Param("token") String token);
}
