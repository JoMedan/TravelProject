package com.example.travelproject.dto;


import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class FriendRequest {
    /**
     * 친구로 추가하고자 하는 대상 사용자의 username
     */
    private String friendUsername;
}
