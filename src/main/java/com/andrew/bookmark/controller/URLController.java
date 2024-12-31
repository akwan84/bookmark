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
import java.util.List;
import java.util.Map;

/**
 * Controller for URL endpoints
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class URLController {
    private URLService urlService;

    private AuthService authService;

    /**
     * Constructor with injected service dependencies
     * @param urlService URL service
     * @param authService Authentication service
     */
    @Autowired
    public URLController(URLService urlService, AuthService authService) {
        this.urlService = urlService;
        this.authService = authService;
    }

    /**
     * POST endpoint to create a new bookmarked URL
     * @param token Authentication token from authorization header
     * @param urlDto Information about the URL to bookmark from request body
     * @return Information about the newly bookmarked URL
     */
    @PostMapping("/url")
    public URLOutputDto createUrl(@RequestHeader("Authorization") String token, @RequestBody URLDto urlDto) {
        User user = this.authService.verify(token);
        return this.urlService.create(urlDto, user);
    }

    /**
     * GET endpoint to redirect a bookmarked URL
     * @param shortCode Short code mapped to a bookmarked URL
     * @return RedirectView to redirect to the appropriate location
     */
    @GetMapping("/url/{code}")
    public RedirectView redirect(@PathVariable("code") String shortCode) {
        return this.urlService.redirect(shortCode);
    }

    /**
     * GET endpoint to get all bookmarked URLs of a user
     * @param token Authentication token from the authorization header for the logged-in user
     * @return List containing all bookmarked URLs and information about them
     */
    @GetMapping("/url")
    public List<URLOutputDto> getUrls(@RequestHeader("Authorization") String token) {
        User user = this.authService.verify(token);
        return this.urlService.getUrls(user);
    }

    /**
     * PUT endpoint to update a bookmarked URLs information (change url, extend expiration, re-activate one-time link, ...)
     * @param token Authentication token from the authorization header for the logged-in user
     * @param shortCode Short code mapped to the bookmarked URL
     * @param newDto Information about the bookmarked URL to update
     * @return Information about the updated bookmarked URL
     */
    @PutMapping("/url/{code}")
    public URLOutputDto updateUrl(@RequestHeader("Authorization") String token, @PathVariable("code") String shortCode, @RequestBody URLDto newDto) {
        User user = this.authService.verify(token);
        return this.urlService.updateUrl(user, newDto, shortCode);
    }

    /**
     * DELETE endpoint to delete a bookmarked URL
     * @param token Authentication token from the authorization header for the logged-in user
     * @param shortCode Short code mapped to the bookmarked URL
     */
    @DeleteMapping("/url/{code}")
    public void deleteUrl(@RequestHeader("Authorization") String token, @PathVariable("code") String shortCode) {
        User user = this.authService.verify(token);
        this.urlService.deleteUrl(user, shortCode);
    }

    /**
     * Exception handler for ResponseStatusException
     * @param e Exception caught
     * @return Formatted response containing error message and HTTP status code
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(
            ResponseStatusException e
    ) {
        Map<String, String> message = new HashMap<>();
        message.put("status", e.getStatusCode() + "");
        message.put("message", e.getReason());
        return new ResponseEntity<>(message, e.getStatusCode());
    }
}
