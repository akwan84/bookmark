package com.andrew.bookmark.service.mapper;

import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User toUser(UserDto dto) {
        String hashedPw = BCrypt.hashpw(dto.password(), BCrypt.gensalt());
        return new User(
                dto.username(),
                hashedPw
        );
    }
}
