package com.example.travelproject.dto;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TokenRefreshRequest {
    private String refreshToken;
}