package com.andrew.bookmark.repository;

import com.andrew.bookmark.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URLRepository extends JpaRepository<URL, Integer> {
    Optional<URL> findByShortCode(String shortCode);
}
