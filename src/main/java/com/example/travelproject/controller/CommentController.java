// src/main/java/com/example/travelproject/controller/CommentController.java
package com.example.travelproject.controller;

import com.example.travelproject.dto.CommentRequest;
import com.example.travelproject.dto.CommentResponse;
import com.example.travelproject.security.JwtTokenProvider;
import com.example.travelproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markers/{markerId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtTokenProvider jwtProvider;

    /** 댓글 작성 */
    @PostMapping
    public ResponseEntity<CommentResponse> writeComment(
            @PathVariable Long markerId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody CommentRequest request
    ) {
        // 1) 토큰 분리
        String token = authHeader.replaceFirst("^Bearer ", "");
        // 2) userId 추출
        Long userId = jwtProvider.getUserIdFromToken(token);
        // 3) 댓글 생성
        CommentResponse resp = commentService.createComment(markerId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    /** 댓글 목록 조회 */
    @GetMapping
    public ResponseEntity<List<CommentResponse>> listComments(
            @PathVariable Long markerId
    ) {
        return ResponseEntity.ok(commentService.listByMarker(markerId));
    }
}
