// src/main/java/com/example/travelproject/repository/UserRepository.java
package com.example.travelproject.repository;

import com.example.travelproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
