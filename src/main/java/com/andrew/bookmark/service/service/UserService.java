package com.andrew.bookmark.service.service;

import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.entity.User;
import com.andrew.bookmark.repository.UserRepository;
import com.andrew.bookmark.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
