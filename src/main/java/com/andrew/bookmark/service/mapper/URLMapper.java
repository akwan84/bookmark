package com.andrew.bookmark.service.mapper;

import com.andrew.bookmark.dto.URLDto;
import com.andrew.bookmark.dto.URLOutputDto;
import com.andrew.bookmark.entity.URL;
import com.andrew.bookmark.entity.User;
import com.andrew.bookmark.service.service.URLService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class URLMapper {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
    private static final int CODE_LENGTH = 6;

    public URL toURL(URLDto dto, User user) {
        URL url = new URL(dto.fullUrl(), generate(), dto.type(), dto.length());
        url.setUser(user);
        return url;
    }

    public URL updateURL(URLDto dto, URL url) {
        url.setFullUrl(dto.fullUrl());
        url.setType(dto.type());
        if(dto.type() == 2) {
            url.setExpirationTime(LocalDateTime.now().plus(dto.length(), ChronoUnit.MINUTES));
        } else if (dto.type() == 3) {
            url.setActive(true);
        }
        return url;
    }

    public URLOutputDto toOutputDto(URL url) {

        return new URLOutputDto(
                url.getFullUrl(),
                url.getShortCode(),
                url.getNumVisits(),
                url.getType(),
                url.getType() == 2 ? url.getExpirationTime() : null
        );
    }

    private String generate() {
        Random random = new Random();
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < URLMapper.CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(URLMapper.CHARS.length());
            str.append(URLMapper.CHARS.charAt(randomIndex));
        }

        return str.toString();
    }

}
