package com.andrew.bookmark.dto;

import java.time.LocalDateTime;

/**
 * Output DTO for bookmarked URLs
 * @param fullUrl URL being bookmarked
 * @param shortCode Short code mapped to the bookmarked URL
 * @param numVisits Number of visits
 * @param type Type (normal, temporary, one-time)
 * @param expiration For temporary links, time the link expires
 */
public record URLOutputDto(
    String fullUrl,
    String shortCode,
    int numVisits,
    int type,
    LocalDateTime expiration
) {

}
