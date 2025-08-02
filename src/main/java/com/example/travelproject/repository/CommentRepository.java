// src/main/java/com/example/travelproject/repository/CommentRepository.java
package com.example.travelproject.repository;

import com.example.travelproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 댓글 CRUD 및 게시물별 댓글 조회용 Repository
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 마커(markerId)에 달린 댓글 목록을 생성일자 순으로 조회
     */
    List<Comment> findByMarkerIdOrderByCreatedAtAsc(Long markerId);
}
