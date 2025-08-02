// src/main/java/com/example/travelproject/mapper/FriendMapper.java
package com.example.travelproject.mapper;

import com.example.travelproject.dto.FriendResponse;
import com.example.travelproject.entity.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FriendMapper {

    @Mapping(source = "friend.id",   target = "friendId")
    @Mapping(source = "user.id",     target = "userId")
    @Mapping(source = "friend.username", target = "username")
    FriendResponse toDto(Friend entity);
}
