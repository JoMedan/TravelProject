// src/main/java/com/example/travelproject/mapper/CommentMapper.java
package com.example.travelproject.mapper;

import com.example.travelproject.dto.CommentRequest;
import com.example.travelproject.dto.CommentResponse;
import com.example.travelproject.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    // content 필드 이름이 같으므로 @Mapping 생략 가능
    Comment toEntity(CommentRequest request);

    @Mapping(source = "id",        target = "commentId")
    @Mapping(source = "marker.id", target = "markerId")
    @Mapping(source = "user.id",   target = "userId")
    CommentResponse toDto(Comment comment);
}
