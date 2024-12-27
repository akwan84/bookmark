package com.andrew.bookmark.dto;

/**
 * Input DTO for URL to bookmark
 * @param fullUrl URL to bookmark
 * @param type Bookmark type: 1 - normal, 2 - temporary, 3 - one-time
 * @param length For temporary links, number of minutes for the link to be active
 */
public record URLDto(
        String fullUrl,
        int type,
        int length
) {

}
