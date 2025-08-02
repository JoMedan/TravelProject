// src/main/java/com/example/travelproject/entity/User.java
package com.example.travelproject.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users")
@Builder(toBuilder = true)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 로그인 ID (unique) */
    @Column(nullable = false, unique = true)
    private String loginId;

    /** 사용자 이름(닉네임) */
    @Column(nullable = false)
    private String username;

    /** 해시된 비밀번호 */
    @Column(nullable = false)
    private String password;

    /** 휴대폰 번호 */
    @Column(nullable = false)
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Marker> markers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RefreshToken> refreshTokens;


    @PrePersist protected void onCreate() { createdAt = updatedAt = LocalDateTime.now(); }
    @PreUpdate  protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
