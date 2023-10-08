package com.wetalk.web.controller;

import com.wetalk.domain.User;
import com.wetalk.dto.RefreshToken;
import com.wetalk.dto.SignedInUser;
import com.wetalk.dto.UserReq;
import com.wetalk.exception.InvalidRefreshTokenException;
import com.wetalk.service.registration.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<SignedInUser> signUp(@Valid @RequestBody UserReq user) {
        return status(HttpStatus.CREATED)
                .body(userService.createUser(user).get());
    }

    @PostMapping("/token")
    public ResponseEntity<SignedInUser> signIn(@Valid @RequestBody SignedInUser signedInUser) {
        User user = userService.findUserByUsername(signedInUser.getUsername());
        if (passwordEncoder.matches(signedInUser.getPassword(), user.getPassword())) {
            return ok(userService.getSignedInUser(user));
        }
        throw new InsufficientAuthenticationException("Unauthorized");
    }

    @PostMapping("/signOut")
    public ResponseEntity<Void> signOut(@Valid @RequestBody RefreshToken refreshToken) {
        userService.removeRefreshToken(refreshToken);
        return accepted().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<SignedInUser> getAccessToken(@Valid @RequestBody RefreshToken refreshToken) {
        return ok(userService.getAccessToken(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new));
    }
}
