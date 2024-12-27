package com.andrew.bookmark.controller;

import com.andrew.bookmark.dto.URLDto;
import com.andrew.bookmark.dto.URLOutputDto;
import com.andrew.bookmark.entity.User;
import com.andrew.bookmark.service.service.AuthService;
import com.andrew.bookmark.service.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class URLController {
    private URLService urlService;

    private AuthService authService;

    @Autowired
    public URLController(URLService urlService, AuthService authService) {
        this.urlService = urlService;
        this.authService = authService;
    }

    @PostMapping("/url")
    public URLOutputDto createUrl(@RequestHeader("Authorization") String token, @RequestBody URLDto urlDto) {
        User user = this.authService.verify(token);
        return this.urlService.create(urlDto, user);
    }

    @GetMapping("/url/{code}")
    public RedirectView redirect(@PathVariable("code") String shortCode) {
        return this.urlService.redirect(shortCode);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleUserNotFoundException(
            ResponseStatusException e
    ) {
        Map<String, String> message = new HashMap<>();
        message.put("status", e.getStatusCode() + "");
        message.put("message", e.getReason());
        return new ResponseEntity<>(message, e.getStatusCode());
    }
}
