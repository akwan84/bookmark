package com.andrew.bookmark.dto;

import java.time.LocalDateTime;

public record URLOutputDto(
    String fullUrl,
    String shortCode,
    int type,
    LocalDateTime expiration
) {

}
