package com.jungchongmu.user_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String university;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UnitType unitType;

    @Column(nullable = false, length = 50)
    private String unitName;

    @Column(length = 255)
    private String profileImageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, length = 50)
    private String role;

    // Enum 정의 필요
    public enum UnitType {
        DEPARTMENT, CLUB, ASSOCIATION
    }
}