package com.example.travelproject.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentResponse {
    private Long commentId;
    private Long markerId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
}
