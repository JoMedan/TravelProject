// src/main/java/com/example/travelproject/service/AuthService.java
package com.example.travelproject.service;

import com.example.travelproject.dto.*;
import com.example.travelproject.entity.RefreshToken;
import com.example.travelproject.entity.User;
import com.example.travelproject.repository.UserRepository;
import com.example.travelproject.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtProvider;
    private final RefreshTokenService refreshSvc;

    /** 회원가입 */
    public void signup(SignupRequest req) {
        if (userRepo.existsByLoginId(req.getLoginId())) {
            throw new RuntimeException("이미 사용중인 Login ID입니다.");
        }
        User u = User.builder()
                .loginId(req.getLoginId())
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .phoneNumber(req.getPhoneNumber())
                .build();
        userRepo.save(u);
    }

    /** 로그인 → Access/Refresh 토큰 발급 */
    public AuthResponse login(LoginRequest req) {
        User user = userRepo.findByLoginId(req.getLoginId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        // **오직 비밀번호만 검증**
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken  = jwtProvider.createAccessToken(user.getId());
        RefreshToken rt     = refreshSvc.createRefreshToken(user.getId());

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(rt.getToken())
                .build();
    }

    /** 리프레시 토큰으로 Access 토큰 갱신 */
    public TokenRefreshResponse refresh(TokenRefreshRequest req) {
        RefreshToken oldRt = refreshSvc.verifyAndGet(req.getRefreshToken());
        Long userId = jwtProvider.getUserIdFromToken(oldRt.getToken());
        String newAccess = jwtProvider.createAccessToken(userId);
        RefreshToken newRt = refreshSvc.createRefreshToken(userId);
        return TokenRefreshResponse.builder()
                .accessToken(newAccess)
                .refreshToken(newRt.getToken())
                .build();
    }

    /** 로그아웃: 리프레시 토큰 삭제 */
    public void logout(String refreshToken) {
        refreshSvc.deleteByToken(refreshToken);
    }
}
