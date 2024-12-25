package com.andrew.bookmark.service.mapper;

import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User toUser(UserDto dto) {
        return new User(
                dto.username(),
                dto.password()
        );
    }
}
