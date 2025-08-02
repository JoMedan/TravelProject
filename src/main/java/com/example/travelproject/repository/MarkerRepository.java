// src/main/java/com/example/travelproject/repository/MarkerRepository.java
package com.example.travelproject.repository;

import com.example.travelproject.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarkerRepository extends JpaRepository<Marker, Long> {
    Optional<Marker> findByIdAndUserId(Long markerId, Long userId);
    List<Marker> findAllByUserId(Long userId);

    List<Marker> findAllByUserIdIn(List<Long> userIds);

}
