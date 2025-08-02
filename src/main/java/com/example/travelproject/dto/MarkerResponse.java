package com.example.travelproject.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MarkerResponse {
    private Long markerId;
    private Long userId;
    private String placeName;
    private String username;
    private String formattedAddress;
    private double placeLat;
    private double placeLng;
    private String content;
    private String imgPath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
