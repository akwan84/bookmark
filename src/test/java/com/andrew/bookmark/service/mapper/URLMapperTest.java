package com.andrew.bookmark.service.mapper;

import com.andrew.bookmark.dto.URLDto;
import com.andrew.bookmark.dto.URLOutputDto;
import com.andrew.bookmark.entity.URL;
import com.andrew.bookmark.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class URLMapperTest {
    private URLMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new URLMapper();
    }

    @Test
    public void shouldMapPermanentURLDtoToURL() {
        URLDto dto = new URLDto("http://www.test.com", 1, 30, true);
        User user = new User("admin", "pass");

        URL url = mapper.toURL(dto, user);

        assertEquals(dto.fullUrl(), url.getFullUrl());
        assertEquals(dto.type(), url.getType());
        assertNull(url.getExpirationTime());
        assertEquals(6, url.getShortCode().length());
        assertEquals(user, url.getUser());
        assertFalse(url.isActive());
    }

    @Test
    public void shouldMapTemporaryURLDtoToURL() {
        URLDto dto = new URLDto("http://www.test.com", 2, 30, true);
        User user = new User("admin", "pass");

        URL url = mapper.toURL(dto, user);

        LocalDateTime time = ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(30).toLocalDateTime();

        assertEquals(dto.fullUrl(), url.getFullUrl());
        assertEquals(dto.type(), url.getType());
        assertNotNull(url.getExpirationTime());
        assertEquals(6, url.getShortCode().length());
        assertEquals(user, url.getUser());
        assertFalse(url.isActive());
    }

    @Test
    public void shouldMapOneTimeURLDtoToURL() {
        URLDto dto = new URLDto("http://www.test.com", 3, 30, true);
        User user = new User("admin", "pass");

        URL url = mapper.toURL(dto, user);

        LocalDateTime time = ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(30).toLocalDateTime();

        assertEquals(dto.fullUrl(), url.getFullUrl());
        assertEquals(dto.type(), url.getType());
        assertNull(url.getExpirationTime());
        assertEquals(6, url.getShortCode().length());
        assertEquals(user, url.getUser());
        assertEquals(dto.active(), url.isActive());
    }

    @Test
    public void shouldUpdateToPermanentURL() {
        URL url = new URL("http://www.test.com", "abc123", 1, 1, true);

        String newUrl = "http://www.test2.com";
        int type = 1;

        URLDto dto = new URLDto(newUrl, type, 30, false);

        URL updated = mapper.updateURL(dto, url);

        assertEquals(newUrl, updated.getFullUrl());
        assertEquals(type, updated.getType());
    }

    @Test
    public void shouldUpdateToTemporaryURL() {
        URL url = new URL("http://www.test.com", "abc123", 1, 1, true);

        String newUrl = "http://www.test2.com";
        int type = 2;
        int minutes = 30;

        LocalDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime();

        URLDto dto = new URLDto(newUrl, type, minutes, false);

        URL updated = mapper.updateURL(dto, url);

        assertEquals(newUrl, updated.getFullUrl());
        assertEquals(type, updated.getType());
        assertEquals(minutes, Duration.between(utcNow, updated.getExpirationTime()).toMinutes());
    }

    @Test
    public void shouldUpdateOneTimeURL() {
        URL url = new URL("http://www.test.com", "abc123", 1, 1, false);

        String newUrl = "http://www.test2.com";
        int type = 3;
        boolean active = true;

        URLDto dto = new URLDto(newUrl, type, 1, active);

        URL updated = mapper.updateURL(dto, url);

        assertEquals(newUrl, updated.getFullUrl());
        assertEquals(type, updated.getType());
        assertEquals(active, updated.isActive());
    }

    @Test
    public void shouldCreatePermanentOutputDtoFromEntity() {
        URL url = new URL("http://www.test.com", "abc123", 1, 1, false);

        URLOutputDto dto = mapper.toOutputDto(url);

        assertEquals(url.getFullUrl(), dto.fullUrl());
        assertEquals(url.getShortCode(), dto.shortCode());
        assertEquals(url.getNumVisits(), dto.numVisits());
        assertEquals(url.getType(), dto.type());
        assertNull(dto.expiration());
        assertTrue(dto.isActive());
    }

    @Test
    public void shouldCreateTemporaryOutputDtoFromEntity() {
        URL url = new URL("http://www.test.com", "abc123", 2, 30, false);

        URLOutputDto dto = mapper.toOutputDto(url);

        assertEquals(url.getFullUrl(), dto.fullUrl());
        assertEquals(url.getShortCode(), dto.shortCode());
        assertEquals(url.getNumVisits(), dto.numVisits());
        assertEquals(url.getType(), dto.type());
        assertEquals(url.getExpirationTime(), dto.expiration());
        assertTrue(dto.isActive());
    }

    @Test
    public void shouldCreateOneTimeOutputDtoFromEntity() {
        URL url = new URL("http://www.test.com", "abc123", 3, 30, false);

        URLOutputDto dto = mapper.toOutputDto(url);

        assertEquals(url.getFullUrl(), dto.fullUrl());
        assertEquals(url.getShortCode(), dto.shortCode());
        assertEquals(url.getNumVisits(), dto.numVisits());
        assertEquals(url.getType(), dto.type());
        assertNull(dto.expiration());
        assertEquals(url.isActive(), dto.isActive());
    }
}