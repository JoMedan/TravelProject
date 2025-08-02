// src/main/java/com/example/travelproject/service/UserService.java
package com.example.travelproject.service;

import com.example.travelproject.dto.UserProfileResponse;
import com.example.travelproject.entity.User;
import com.example.travelproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public UserProfileResponse getMyProfile(Long userId) {
        User me = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다. id=" + userId));
        return UserProfileResponse.builder()
                .id(me.getId())
                .loginId(me.getLoginId())
                .username(me.getUsername())
                .phoneNumber(me.getPhoneNumber())
                .createdAt(me.getCreatedAt())
                .updatedAt(me.getUpdatedAt())
                .build();
    }
}
