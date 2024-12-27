package com.andrew.bookmark.service.service;

import com.andrew.bookmark.dto.URLDto;
import com.andrew.bookmark.dto.URLOutputDto;
import com.andrew.bookmark.entity.URL;
import com.andrew.bookmark.entity.User;
import com.andrew.bookmark.repository.URLRepository;
import com.andrew.bookmark.service.mapper.URLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Random;

@Service
public class URLService {
    private final URLRepository urlRepository;

    private final URLMapper urlMapper;

    @Autowired
    public URLService(URLRepository urlRepository, URLMapper urlMapper) {
        this.urlRepository = urlRepository;
        this.urlMapper = urlMapper;
    }

    public URLOutputDto create(URLDto dto, User user) {
        if(dto.type() == 2 && dto.length() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Temporary links must have a lifetime of 1 minute or more");
        }

        URL url = this.urlMapper.toURL(dto, user);
        //making sure short code generated is unique
        while(this.urlRepository.findByShortCode(url.getShortCode()).isPresent()) {
            url = this.urlMapper.toURL(dto, user);
        }

        urlRepository.save(url);

        return urlMapper.toOutputDto(url);
    }
}
