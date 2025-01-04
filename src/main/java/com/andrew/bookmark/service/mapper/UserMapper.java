package com.andrew.bookmark.service.mapper;

import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Mapper from User entity
 */
@Service
public class UserMapper {
    /**
     * Map user input DTO to User entity and hash password
     * @param dto Input dto
     * @return Mapped User entity
     */
    public User toUser(UserDto dto) {
        String hashedPw = BCrypt.hashpw(dto.password(), BCrypt.gensalt());
        return new User(
                dto.username(),
                hashedPw
        );
    }
}
