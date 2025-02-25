package com.andrew.bookmark.controller;

import com.andrew.bookmark.dto.URLDto;
import com.andrew.bookmark.dto.URLOutputDto;
import com.andrew.bookmark.entity.User;
import com.andrew.bookmark.exception.url.ForbiddenUpdateException;
import com.andrew.bookmark.exception.url.InvalidInputException;
import com.andrew.bookmark.exception.url.InvalidLinkException;
import com.andrew.bookmark.exception.url.UrlNotFoundException;
import com.andrew.bookmark.service.service.AuthService;
import com.andrew.bookmark.service.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for URL endpoints
 */
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
     * @param token Authentication token from cookie
     * @param urlDto Information about the URL to bookmark from request body
     * @return Information about the newly bookmarked URL
     */
    @PostMapping("/url")
    public URLOutputDto createUrl(@CookieValue("authToken") String token, @RequestBody URLDto urlDto) {
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
     * @param token Authentication token from cookie
     * @return List containing all bookmarked URLs and information about them
     */
    @GetMapping("/url")
    public List<URLOutputDto> getUrls(@CookieValue("authToken") String token) {
        User user = this.authService.verify(token);
        return this.urlService.getUrls(user);
    }

    /**
     * PUT endpoint to update a bookmarked URLs information (change url, extend expiration, re-activate one-time link, ...)
     * @param token Authentication token from cookie
     * @param shortCode Short code mapped to the bookmarked URL
     * @param newDto Information about the bookmarked URL to update
     * @return Information about the updated bookmarked URL
     */
    @PutMapping("/url/{code}")
    public URLOutputDto updateUrl(@CookieValue("authToken") String token, @PathVariable("code") String shortCode, @RequestBody URLDto newDto) {
        User user = this.authService.verify(token);
        return this.urlService.updateUrl(user, newDto, shortCode);
    }

    /**
     * DELETE endpoint to delete a bookmarked URL
     * @param token Authentication token from cookie
     * @param shortCode Short code mapped to the bookmarked URL
     */
    @DeleteMapping("/url/{code}")
    public void deleteUrl(@CookieValue("authToken") String token, @PathVariable("code") String shortCode) {
        User user = this.authService.verify(token);
        this.urlService.deleteUrl(user, shortCode);
    }

    /**
     * Exception handler for InvalidInputException
     * @param exception Exception caught
     * @return Formatted response containing error message and status code
     */
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleInvalidInputException(InvalidInputException exception) {
        Map<String, String> message = new HashMap<>();
        message.put("status", "400");
        message.put("message", exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for ForbiddenUpdateException and InvalidLinkException
     * @param exception Exception caught
     * @return Formatted response containing error message and status code
     */
    @ExceptionHandler({ ForbiddenUpdateException.class, InvalidLinkException.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleForbiddenException(RuntimeException exception) {
        Map<String, String> message = new HashMap<>();
        message.put("status", "403");
        message.put("message", exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    /**
     * Exception handler for UrlNotFoundException
     * @param exception Exception caught
     * @return Formatted response containing error message and status code
     */
    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleUrlNotFoundException(UrlNotFoundException exception) {
        Map<String, String> message = new HashMap<>();
        message.put("status", "404");
        message.put("message", exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for exceptions not explicitly thrown
     * @param exception Exception caught
     * @return Formatted response containing error message and HTTP status code
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        Map<String, String> message = new HashMap<>();
        message.put("status", "500");
        message.put("message", exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
