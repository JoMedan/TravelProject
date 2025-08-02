package com.example.travelproject.mapper;

import com.example.travelproject.dto.MarkerRequest;
import com.example.travelproject.dto.MarkerResponse;
import com.example.travelproject.entity.Marker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MarkerMapper {

    /**
     * MarkerRequest → Marker 엔티티 변환
     * 필드명이 동일한 자바 필드는 MapStruct가 자동 매핑해 줍니다.
     */
    Marker toEntity(MarkerRequest request);

    /**
     * Marker 엔티티 → MarkerResponse 변환
     * - id         → markerId
     * - user.id    → userId
     * - user.username → username (새로 추가)
     * 나머지 필드(placeName, formattedAddress 등)는 이름이 같으므로 자동 매핑됩니다.
     */
    @Mapping(source = "id",            target = "markerId")
    @Mapping(source = "user.id",       target = "userId")
    @Mapping(source = "user.username", target = "username")
    MarkerResponse toDto(Marker marker);

    /**
     * 엔티티 리스트 → DTO 리스트 변환
     */
    List<MarkerResponse> toDtoList(List<Marker> markers);
}
