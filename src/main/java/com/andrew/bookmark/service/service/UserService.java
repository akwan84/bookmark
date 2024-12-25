package com.andrew.bookmark.service.service;

import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.entity.User;
import com.andrew.bookmark.repository.UserRepository;
import com.andrew.bookmark.service.mapper.UserMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void registerUser(UserDto dto){
        User user = this.mapper.toUser(dto);
        this.repository.save(user);
    }

    public ResponseEntity<String> loginUser(UserDto dto) {
        Optional<User> foundUser = this.repository.findByUsername(dto.username());
        if(!foundUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        boolean match = BCrypt.checkpw(dto.password(), foundUser.get().getPassword());

        if(!match) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is incorrect");
        }

        return ResponseEntity.ok("Login Success");
    }
}
