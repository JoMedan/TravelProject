// src/main/java/com/example/travelproject/service/CommentService.java
package com.example.travelproject.service;

import com.example.travelproject.dto.CommentRequest;
import com.example.travelproject.dto.CommentResponse;
import com.example.travelproject.entity.Comment;
import com.example.travelproject.entity.Marker;
import com.example.travelproject.entity.User;
import com.example.travelproject.mapper.CommentMapper;
import com.example.travelproject.repository.CommentRepository;
import com.example.travelproject.repository.MarkerRepository;
import com.example.travelproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepo;
    private final MarkerRepository markerRepo;
    private final UserRepository userRepo;
    private final CommentMapper mapper;

    /** 댓글 작성 */
    public CommentResponse createComment(Long markerId, Long userId, CommentRequest request) {
        Marker marker = markerRepo.findById(markerId)
                .orElseThrow(() -> new RuntimeException("마커를 찾을 수 없습니다. id=" + markerId));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. id=" + userId));

        Comment comment = mapper.toEntity(request);
        comment.setMarker(marker);
        comment.setUser(user);

        Comment saved = commentRepo.save(comment);
        return mapper.toDto(saved);
    }

    /** 특정 마커의 댓글 목록 */
    public List<CommentResponse> listByMarker(Long markerId) {
        return commentRepo.findByMarkerIdOrderByCreatedAtAsc(markerId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
