// src/main/java/com/example/travelproject/repository/FriendRepository.java
package com.example.travelproject.repository;

import com.example.travelproject.entity.Friend;
import com.example.travelproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByUserAndFriend(User user, User friend);
    Optional<Friend> findByUserAndFriend(User user, User friend);

    /**
     * 내(userId)가 추가한 친구(frie​ndId) 관계가 존재하는지 조회
     */
    Optional<Friend> findByUserIdAndFriendId(Long userId, Long friendId);

    /**
     * 내(userId)가 추가한 모든 친구 관계 조회
     */
    List<Friend> findAllByUserId(Long userId);

    /**
     * 내 userId 가 추가한 친구 중, 친구의 username 으로 관계를 조회
     */
    Optional<Friend> findByUserIdAndFriendUsername(Long userId, String friendUsername);
}
