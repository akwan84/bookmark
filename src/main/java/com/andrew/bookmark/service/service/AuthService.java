package com.andrew.bookmark.service.service;

import com.andrew.bookmark.entity.Token;
import com.andrew.bookmark.entity.User;
import com.andrew.bookmark.repository.TokenRepository;
import com.andrew.bookmark.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    public AuthService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public User verify(String token) {
        Optional<Token> foundToken = this.tokenRepository.findByToken(token);
        if(!foundToken.isPresent() || isExpired(foundToken.get().getExpirationTime())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return this.userRepository.findUserWithToken(token).get();
    }

    private boolean isExpired(LocalDateTime expirationDate) {
        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(expirationDate);
    }
}
