// src/main/java/com/example/travelproject/dto/SignupRequest.java
package com.example.travelproject.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    /** 로그인 ID로 사용할 고유 문자열 */
    @NotBlank(message = "Login ID는 필수입니다.")
    private String loginId;

    /** 사용자 이름(닉네임) */
    @NotBlank(message = "사용자 이름은 필수입니다.")
    private String username;

    /** 비밀번호 */
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    /** 휴대폰 번호 */
    @NotBlank(message = "전화번호는 필수입니다.")
    private String phoneNumber;
}
