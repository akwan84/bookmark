package com.andrew.bookmark.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

/**
 * Input DTO for URL to bookmark
 * @param fullUrl URL to bookmark
 * @param type Bookmark type: 1 - normal, 2 - temporary, 3 - one-time
 * @param length For temporary links, number of minutes for the link to be active
 */
public record URLDto(
        @NotEmpty
        String fullUrl,
        @NotEmpty
        int type,
        int length
) {

}
