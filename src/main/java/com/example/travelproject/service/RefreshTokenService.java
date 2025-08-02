// src/main/java/com/example/travelproject/service/RefreshTokenService.java
package com.example.travelproject.service;

import com.example.travelproject.entity.RefreshToken;
import com.example.travelproject.entity.User;
import com.example.travelproject.repository.RefreshTokenRepository;
import com.example.travelproject.repository.UserRepository;
import com.example.travelproject.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repo;
    private final UserRepository userRepo;
    private final JwtTokenProvider jwtProvider;

    /** 새 리프레시 토큰 생성 */
    public RefreshToken createRefreshToken(Long userId) {
        // 1) JWT 프로바이더로 토큰 발행
        String token = jwtProvider.createRefreshToken(userId);

        // 2) userId 로 User 엔티티 레퍼런스 (프록시) 가져오기
        User user = userRepo.getReferenceById(userId);

        // 3) 만료시간 계산
        Instant expiry = Instant.now().plusMillis(jwtProvider.getRefreshTokenExpireMs());

        // 4) 엔티티 빌드 & 저장
        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(token)
                .expiryDate(expiry)
                .build();
        return repo.save(rt);
    }

    /** 토큰 문자열로 엔티티 조회 */
    public RefreshToken getByToken(String token) {
        return repo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("리프레시 토큰 미존재"));
    }

    /** 조회 + 만료 검사 (만료 시 삭제 후 예외) */
    public RefreshToken verifyAndGet(String token) {
        RefreshToken rt = getByToken(token);
        if (rt.getExpiryDate().isBefore(Instant.now())) {
            repo.delete(rt);
            throw new RuntimeException("리프레시 토큰 만료됨");
        }
        return rt;
    }

    /** 토큰 문자열로 엔티티 삭제 */
    public void deleteByToken(String token) {
        repo.findByToken(token).ifPresent(repo::delete);
    }
}
