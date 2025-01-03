package com.andrew.bookmark.service.service;

import com.andrew.bookmark.dto.TokenResponseDto;
import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.entity.Token;
import com.andrew.bookmark.entity.User;
import com.andrew.bookmark.repository.TokenRepository;
import com.andrew.bookmark.repository.UserRepository;
import com.andrew.bookmark.service.mapper.UserMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private static final int LENGTH = 15;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repository, TokenRepository tokenRepository, UserMapper mapper) {
        this.userRepository = repository;
        this.tokenRepository = tokenRepository;
        this.mapper = mapper;
    }

    public void registerUser(UserDto dto){
        Optional<User> foundUser = this.userRepository.findByUsername(dto.username());
        if(foundUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username taken");
        }

        User user = this.mapper.toUser(dto);
        this.userRepository.save(user);
    }

    public void loginUser(UserDto dto, HttpServletResponse response) {
        Optional<User> foundUser = this.userRepository.findByUsername(dto.username());
        if(!foundUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        boolean match = BCrypt.checkpw(dto.password(), foundUser.get().getPassword());

        if(!match) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is incorrect");
        }

        Optional<Token> foundToken = this.tokenRepository.findUserWithTokenById(foundUser.get().getId());
        if(foundToken.isPresent()) {
            Token token = foundToken.get();
            token.setToken(UUID.randomUUID().toString());
            token.setExpirationTime(ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(UserService.LENGTH).toLocalDateTime());

            setCookie(response, token.getToken());

            this.tokenRepository.save(token);
        }else{
            Token token = new Token();
            token.setUser(foundUser.get());

            setCookie(response, token.getToken());

            this.tokenRepository.save(token);
        }
    }

    private void setCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("authToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setHeader("Set-Cookie","authToken=" + token + "; HttpOnly; Path=/; SameSite=None; Secure");
    }

    public void logoutUser(String token, HttpServletResponse response) {
        //Need to make sure something is actually going to be deleted
        Optional<Token> foundToken = this.tokenRepository.findByToken(token);
        if(!foundToken.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token does not exist");
        }

        Cookie cookie = new Cookie("authToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        this.tokenRepository.deleteToken(token);
    }
}
