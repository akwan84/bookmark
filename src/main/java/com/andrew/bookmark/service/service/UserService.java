package com.andrew.bookmark.service.service;

import com.andrew.bookmark.dto.TokenResponseDto;
import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.entity.Token;
import com.andrew.bookmark.entity.User;
import com.andrew.bookmark.repository.TokenRepository;
import com.andrew.bookmark.repository.UserRepository;
import com.andrew.bookmark.service.mapper.UserMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
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

    public ResponseEntity<TokenResponseDto> loginUser(UserDto dto) {
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
            token.setExpirationTime(LocalDateTime.now().plus(30, ChronoUnit.MINUTES));
            this.tokenRepository.save(token);
            return ResponseEntity.ok(new TokenResponseDto(token.getToken()));
        }else{
            Token token = new Token();
            token.setUser(foundUser.get());
            this.tokenRepository.save(token);
            return ResponseEntity.ok(new TokenResponseDto(token.getToken()));
        }
    }

    public void logoutUser(String token) {
        String tokenString = token.startsWith("Bearer ")
                ? token.substring(7)
                : token;

        //Need to make sure something is actually going to be deleted
        Optional<Token> foundToken = this.tokenRepository.findByToken(tokenString);
        if(!foundToken.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token does not exist");
        }

        this.tokenRepository.deleteToken(tokenString);
    }
}
