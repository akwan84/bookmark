package com.andrew.bookmark.dto;

import jakarta.validation.constraints.NotEmpty;

/**
 * Input DTO for Users
 * @param username Username, must not be empty
 * @param password Password, must not be empty
 */
public record UserDto (
        @NotEmpty
        String username,
        @NotEmpty
        String password
) {
}
