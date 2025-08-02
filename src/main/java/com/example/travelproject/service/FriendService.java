// src/main/java/com/example/travelproject/service/FriendService.java
package com.example.travelproject.service;

import com.example.travelproject.dto.FriendResponse;
import com.example.travelproject.entity.Friend;
import com.example.travelproject.entity.User;
import com.example.travelproject.repository.FriendRepository;
import com.example.travelproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepo;
    private final FriendRepository friendRepo;

    /** username 으로 친구 추가 */
    public FriendResponse addFriend(Long myUserId, String friendUsername) {
        // 1) 내 유저
        User me = userRepo.findById(myUserId)
                .orElseThrow(() -> new RuntimeException("로그인 정보가 유효하지 않습니다."));
        // 2) 친구로 추가할 대상
        User target = userRepo.findByUsername(friendUsername)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다: " + friendUsername));
        // 3) 중복 체크
        if (friendRepo.existsByUserAndFriend(me, target)) {
            throw new RuntimeException("이미 친구로 등록된 사용자입니다.");
        }
        // 4) 저장
        Friend rel = Friend.builder()
                .user(me)
                .friend(target)
                .build();
        Friend saved = friendRepo.save(rel);

        // 5) FriendResponse로 매핑해서 반환
        return FriendResponse.builder()
                .friendId(saved.getId())
                .userId(me.getId())
                .friendUserId(target.getId())
                .username(target.getUsername())
                .joinedAt(target.getCreatedAt())
                .build();
    }
    /** 내 친구 목록 조회 */
    public List<FriendResponse> listFriends(Long myUserId) {
        return friendRepo.findAllByUserId(myUserId).stream()
                .map(rel -> FriendResponse.builder()
                        .friendId(rel.getId())
                        .userId(rel.getUser().getId())
                        .friendUserId(rel.getFriend().getId())
                        .username(rel.getFriend().getUsername())
                        .joinedAt(rel.getFriend().getCreatedAt())
                        .build()
                )
                .toList();
    }


    public FriendResponse getFriendByUsername(Long myUserId, String friendUsername) {
        // 1) 내 계정이 유효한지 확인
        userRepo.findById(myUserId)
                .orElseThrow(() -> new RuntimeException("로그인 정보가 유효하지 않습니다."));

        // 2) repository 에서 userId + friend.username 으로 찾아서 없으면 예외
        Friend rel = friendRepo.findByUserIdAndFriendUsername(myUserId, friendUsername)
                .orElseThrow(() -> new RuntimeException("친구가 아닙니다: " + friendUsername));

        // 3) 엔티티 → DTO 변환
        return toDto(rel);
    }

    /** 엔티티 → DTO 헬퍼 */
    private FriendResponse toDto(Friend rel) {
        User friend = rel.getFriend();
        return FriendResponse.builder()
                .friendId(rel.getId())
                .userId(rel.getUser().getId())
                .friendUserId(friend.getId())
                .username(friend.getUsername())
                .joinedAt(friend.getCreatedAt())
                .build();
    }

}
