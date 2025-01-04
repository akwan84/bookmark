package com.andrew.bookmark.service.service;

import com.andrew.bookmark.entity.Token;
import com.andrew.bookmark.entity.User;
import com.andrew.bookmark.repository.TokenRepository;
import com.andrew.bookmark.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Authentication service
 */
@Service
public class AuthService {
    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    /**
     * Constructor
     * @param userRepository Repository for persisting User entities
     * @param tokenRepository Repository for persisting Token entities
     */
    public AuthService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Verifying authentication token
     * @param token Token to verify
     * @return User associated with the token if valid
     */
    public User verify(String token) {
        Optional<Token> foundToken = this.tokenRepository.findByToken(token);
        if(!foundToken.isPresent() || isExpired(foundToken.get().getExpirationTime())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return this.userRepository.findUserWithToken(token).get();
    }

    /**
     * Check if a token expiration date is expired
     * @param expirationDate expiration date
     * @return true if expired, false otherwise
     */
    private boolean isExpired(LocalDateTime expirationDate) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));

        return now.toLocalDateTime().isAfter(expirationDate);
    }
}
