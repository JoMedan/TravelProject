package com.example.travelproject.service;

import com.example.travelproject.dto.MarkerRequest;
import com.example.travelproject.dto.MarkerResponse;
import com.example.travelproject.entity.Friend;
import com.example.travelproject.entity.Marker;
import com.example.travelproject.entity.User;
import com.example.travelproject.mapper.MarkerMapper;
import com.example.travelproject.repository.FriendRepository;
import com.example.travelproject.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkerService {
    private final MarkerRepository repo;
    private final MarkerMapper mapper;
    private final MarkerRepository markerRepo;
    private final FriendRepository friendRepo;

    public MarkerResponse create(MarkerRequest request, Long userId) {
        // 1) DTO → Entity
        Marker entity = mapper.toEntity(request);
        // 2) 연관된 User 세팅 (PK만 필요)
        entity.setUser(User.builder().id(userId).build());
        // 3) 저장 & Response DTO 변환
        Marker saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public List<MarkerResponse> listAll() {
        return mapper.toDtoList(repo.findAll());
    }

    public MarkerResponse get(Long markerId) {
        Marker entity = repo.findById(markerId)
                .orElseThrow(()->new RuntimeException("해당 마커를 찾을 수 없습니다. id="+markerId));
        return mapper.toDto(entity);
    }
    public List<MarkerResponse> listByUser(Long userId) {
        List<Marker> markers = repo.findAllByUserId(userId);
        return mapper.toDtoList(markers);
    }

    public MarkerResponse getByUser( Long userId, Long markerId) {
        Marker entity = repo.findByIdAndUserId(markerId, userId).
                orElseThrow(() -> new RuntimeException(" 유저 id=" + userId+" 의 마커를 찾을 수 없습니다. markerId =" + markerId));
        return mapper.toDto(entity);
    }



    /** 내가 맺은 친구들이 올린 마커 전체 조회 */
    public List<MarkerResponse> listFriendsMarkers(Long myUserId) {
        // 1) 내 친구 관계 전부
        List<Friend> relations = friendRepo.findAllByUserId(myUserId);
        // 2) 친구 ID들만 추출
        List<Long> friendIds = relations.stream()
                .map(rel -> rel.getFriend().getId())
                .toList();
        // 3) 친구들이 올린 마커 조회
        List<Marker> markers = markerRepo.findAllByUserIdIn(friendIds);
        // 4) DTO 변환 후 반환
        return mapper.toDtoList(markers);
    }

}
