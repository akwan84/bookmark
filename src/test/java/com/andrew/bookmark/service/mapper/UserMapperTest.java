package com.andrew.bookmark.service.mapper;

import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserMapper();
    }

    @Test
    public void shouldMapUserEntityToDto() {
        UserDto dto = new UserDto("test", "abc123");

        User user = mapper.toUser(dto);

        assertEquals(dto.username(), user.getUsername());
        assertTrue(BCrypt.checkpw(dto.password(), user.getPassword()));
    }
}