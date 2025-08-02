package com.example.travelproject.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MarkerRequest {
    /** 장소명(필수) */
    private String placeName;
    /** 주소(NULL 허용) */
    private String formattedAddress;
    /** 위도(필수) */
    private double placeLat;
    /** 경도(필수) */
    private double placeLng;
    /** 본문 내용(필수) */
    private String content;
    /** 사진 경로(필수) */
    private String imgPath;
}
