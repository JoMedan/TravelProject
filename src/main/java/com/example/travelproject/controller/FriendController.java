// src/main/java/com/example/travelproject/controller/FriendController.java
package com.example.travelproject.controller;

import com.example.travelproject.dto.FriendResponse;
import com.example.travelproject.security.JwtTokenProvider;
import com.example.travelproject.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final JwtTokenProvider jwtProvider;

    /** 친구 추가 (username 으로) */
    @PostMapping("/by-username")
    public ResponseEntity<FriendResponse> addFriend(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestParam String friendUsername
    ) {
        Long myUserId = jwtProvider.getUserIdFromToken(authHeader.replaceFirst("^Bearer ", ""));
        FriendResponse dto = friendService.addFriend(myUserId, friendUsername);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    /** 내 친구 전체 목록 조회 */
    @GetMapping("/me")
    public ResponseEntity<List<FriendResponse>> listMyFriends(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        Long myUserId = jwtProvider.getUserIdFromToken(authHeader.replaceFirst("^Bearer ", ""));
        List<FriendResponse> friends = friendService.listFriends(myUserId);
        return ResponseEntity.ok(friends);
    }



    @Operation(
            summary = "친구 정보 조회 (username 기준)",
            description = "헤더의 Bearer 토큰으로 내 userId를 확인하고, URL 경로의 friendUsername 으로 친구관계를 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/by-username/{friendUsername}")
    public ResponseEntity<FriendResponse> getFriendByUsername(
            @PathVariable String friendUsername,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        String token    = authHeader.replaceFirst("^Bearer ", "");
        Long myUserId   = jwtProvider.getUserIdFromToken(token);
        FriendResponse resp = friendService.getFriendByUsername(myUserId, friendUsername);
        return ResponseEntity.ok(resp);
    }
}
