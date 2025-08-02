// src/main/java/com/example/travelproject/security/JwtAuthenticationFilter.java
package com.example.travelproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;
@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        // swagger 와 auth API 는 필터 동작 대상에서 빼 버린다
        return path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/webjars")
                || path.startsWith("/api/auth");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws java.io.IOException, jakarta.servlet.ServletException {
        // (1) Authorization 헤더로부터 토큰 추출
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtProvider.validateToken(token)) {
                // 토큰이 유효하다면 SecurityContext 에 인증을 세팅
                Long userId = jwtProvider.getUserIdFromToken(token);
                // ...UserDetailsService 로부터 UserDetails 를 조회해도 되고,
                // 그냥 userId 로 간단하게 Authentication 토큰 생성해 넣어도 됩니다.
                var auth = new org.springframework.security.authentication.
                        UsernamePasswordAuthenticationToken(userId, null, List.of());
                org.springframework.security.core.context.SecurityContextHolder
                        .getContext().setAuthentication(auth);
            }
        }
        // (2) 필터 체인 계속 진행
        chain.doFilter(req, res);
    }
}
