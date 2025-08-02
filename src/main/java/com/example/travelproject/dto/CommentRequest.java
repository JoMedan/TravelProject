// src/main/java/com/example/travelproject/dto/CommentRequest.java
package com.example.travelproject.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CommentRequest {
    /** 댓글 본문 */
    private String content;
}
