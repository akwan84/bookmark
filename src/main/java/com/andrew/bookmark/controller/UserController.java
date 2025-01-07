package com.andrew.bookmark.controller;

import com.andrew.bookmark.dto.TokenResponseDto;
import com.andrew.bookmark.dto.UserDto;
import com.andrew.bookmark.exception.url.InvalidInputException;
import com.andrew.bookmark.exception.user.DuplicateUserException;
import com.andrew.bookmark.exception.user.NonExistentTokenException;
import com.andrew.bookmark.exception.user.UnauthorizedUserException;
import com.andrew.bookmark.exception.user.UserNotFoundException;
import com.andrew.bookmark.service.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * Exception handler for UnauthorizedUserException
     * @param exception Exception caught
     * @return Formatted response containing error message and status code
     */
    @ExceptionHandler(UnauthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleUnauthorizedUserException(UnauthorizedUserException exception) {
        Map<String, String> message = new HashMap<>();
        message.put("status", "401");
        message.put("message", exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Exception handler for NonExistentTokenException and UserNotFoundException
     * @param exception Exception caught
     * @return Formatted response containing error message and status code
     */
    @ExceptionHandler({NonExistentTokenException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundException(RuntimeException exception) {
        Map<String, String> message = new HashMap<>();
        message.put("status", "404");
        message.put("message", exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for DuplicateUserException
     * @param exception Exception caught
     * @return Formatted response containing error message and status code
     */
    @ExceptionHandler(DuplicateUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> handleDuplicateUserException(InvalidInputException exception) {
        Map<String, String> message = new HashMap<>();
        message.put("status", "409");
        message.put("message", exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
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
