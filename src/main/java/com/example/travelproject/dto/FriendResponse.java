// src/main/java/com/example/travelproject/dto/FriendResponse.java
package com.example.travelproject.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class FriendResponse {
    private Long friendId;
    private Long userId;         // 내 사용자 ID
    private Long friendUserId;      // 친구(상대) 사용자 ID

    private String username;        // 친구 사용자 이름
    private String email;           // 친구 이메일
    private LocalDateTime joinedAt; // 친구 가입일

}
