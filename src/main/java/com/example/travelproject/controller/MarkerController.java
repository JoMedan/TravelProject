// src/main/java/com/example/travelproject/controller/MarkerController.java
package com.example.travelproject.controller;

import com.example.travelproject.dto.MarkerRequest;
import com.example.travelproject.dto.MarkerResponse;
import com.example.travelproject.entity.Marker;
import com.example.travelproject.security.JwtTokenProvider;
import com.example.travelproject.service.MarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markers")
@RequiredArgsConstructor
public class MarkerController {

    private final MarkerService service;
    private final JwtTokenProvider jwtProvider;

    @PostMapping
    public ResponseEntity<MarkerResponse> create(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody MarkerRequest request
    ) {
        String token = authHeader.replaceFirst("^Bearer ", "");
        Long userId = jwtProvider.getUserIdFromToken(token);
        MarkerResponse resp = service.create(request, userId);
        return ResponseEntity.ok(resp);
    }

    /**
     * 전체 조회 (관리자용 혹은 공개 전용)
     */
    @GetMapping
    public ResponseEntity<List<MarkerResponse>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    /**
     * 내가 찍은 마커 전체 조회
     */
    @GetMapping("/me")
    public ResponseEntity<List<MarkerResponse>> listMyMarkers(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        String token = authHeader.replaceFirst("^Bearer ", "");
        Long userId = jwtProvider.getUserIdFromToken(token);
        return ResponseEntity.ok(service.listByUser(userId));
    }

    /**
     * 내가 찍은 특정 마커 상세 조회
     */
    @GetMapping("/{markerId}")
    public ResponseEntity<MarkerResponse> getMyMarker(
            @PathVariable Long markerId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        String token = authHeader.replaceFirst("^Bearer ", "");
        Long userId = jwtProvider.getUserIdFromToken(token);
        MarkerResponse resp = service.getByUser(userId, markerId);
        return ResponseEntity.ok(resp);
    }

    /**
     * 공개 마커 상세 조회
     */
    @GetMapping("/public/{markerId}")
    public ResponseEntity<MarkerResponse> getPublicMarker(
            @PathVariable Long markerId
    ) {
        return ResponseEntity.ok(service.get(markerId));
    }


    /**
     * 내가 맺은 친구들이 올린 게시물 전체 조회
     */
    @GetMapping("/friends")
    public ResponseEntity<List<MarkerResponse>> listFriendsMarkers(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader
    ) {
        String token = authHeader.replaceFirst("^Bearer ", "");
        Long myUserId = jwtProvider.getUserIdFromToken(token);
        List<MarkerResponse> resp = service.listFriendsMarkers(myUserId);

        return ResponseEntity.ok(resp);
    }
}
