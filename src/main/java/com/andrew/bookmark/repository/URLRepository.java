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

/**
 * Repository for URL entities
 */
public interface URLRepository extends JpaRepository<URL, Integer> {
    /**
     * Query to find bookmarked URLs by their mapped short codes
     * @param shortCode short code
     * @return URL entity if it exists
     */
    Optional<URL> findByShortCode(String shortCode);

    /**
     * Query to find URLs bookmarked by a user
     * @param user user
     * @return URLs bookmarked by the user
     */
    List<URL> findAllByUser(User user);

    /**
     * Query to delete a bookmarked URL using associated short code
     * @param shortCode short code
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM url WHERE short_code = :code", nativeQuery = true)
    void deleteUrl(@Param("code") String shortCode);
}
