// src/main/java/com/example/travelproject/dto/LoginRequest.java
package com.example.travelproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    /** 로그인에 사용할 고유 ID (예: 이메일 또는 별도 생성 아이디) */
    private String loginId;


    /** 비밀번호 */
    private String password;


}
