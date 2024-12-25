package com.andrew.bookmark.controller;

import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/user")
    public void registerUser(@Valid @RequestBody UserDto dto) {
        this.service.registerUser(dto);
    }
}
