package com.andrew.bookmark.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserDto (
        @NotEmpty
        String username,
        @NotEmpty
        String password
) {
}
