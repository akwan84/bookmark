package com.andrew.bookmark.repository;

import com.andrew.bookmark.entity.URL;
import com.andrew.bookmark.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface URLRepository extends JpaRepository<URL, Integer> {
    Optional<URL> findByShortCode(String shortCode);

    List<URL> findAllByUser(User user);
}
