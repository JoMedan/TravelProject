// src/main/java/com/example/travelproject/controller/UserController.java
package com.example.travelproject.controller;

import com.example.travelproject.dto.UserProfileResponse;
import com.example.travelproject.security.JwtTokenProvider;
import com.example.travelproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtProvider;
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        String token  = authHeader.replaceFirst("^Bearer ", "");
        Long userId   = jwtProvider.getUserIdFromToken(token);
        UserProfileResponse profile = userService.getMyProfile(userId);
        return ResponseEntity.ok(profile);
    }
}
