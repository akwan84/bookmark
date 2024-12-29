package com.andrew.bookmark.repository;

import com.andrew.bookmark.entity.URL;
import com.andrew.bookmark.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface URLRepository extends JpaRepository<URL, Integer> {
    Optional<URL> findByShortCode(String shortCode);

    List<URL> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM url WHERE short_code = :code", nativeQuery = true)
    void deleteUrl(@Param("code") String shortCode);
}
