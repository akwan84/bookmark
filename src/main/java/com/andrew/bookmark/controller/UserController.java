package com.andrew.bookmark.controller;

import com.andrew.bookmark.dto.TokenResponseDto;
import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.service.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for User endpoints
 */
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") //temporary solution to fix CORS issue, will change later
@RestController
public class UserController {
    private final UserService service;

    /**
     * Constructor with injected dependencies
     * @param service User service
     */
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * POST endpoint to register a new user
     * @param dto Username and password of the user to register
     */
    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody UserDto dto) {
        this.service.registerUser(dto);
    }

    /**
     * POST endpoint to log in a user
     * @param dto Username and password of the user to login
     * @param response Response object
     * @return Authentication token upon successful login
     */
    @PostMapping("/login")
    public void loginUser(@Valid @RequestBody UserDto dto, HttpServletResponse response) {
        this.service.loginUser(dto, response);
    }

    /**
     * POST endpoint to log out a user
     * @param token Authentication token of the user to logout
     * @param response Response object
     */
    @PostMapping("/logout")
    public void logout(@CookieValue("authToken")String token,  HttpServletResponse response) {
        this.service.logoutUser(token, response);
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
