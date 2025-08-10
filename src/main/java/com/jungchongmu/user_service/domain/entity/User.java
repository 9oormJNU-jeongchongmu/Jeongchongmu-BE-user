package com.jungchongmu.user_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_users_oauth",
                columnNames = {"oauth_provider", "oauth_id"}
        ),
        indexes = @Index(
                name = "idx_users_univ_unit",
                columnList = "university, unit_type, unit_name"
        )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    /* ========== PK ========== */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    /* ========== 소셜 식별(고정) ========== */
    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider", nullable = false, length = 20, updatable = false)
    private AuthProvider oauthProvider; // KAKAO

    @Column(name = "oauth_id", nullable = false, length = 100, updatable = false)
    private String oauthId;

    /* ========== 사용자 정보(고정) ========== */
    @Column(name = "name", nullable = false, length = 50, updatable = false)
    private String name;

    @Column(name = "profile_image_url", length = 255, updatable = false)
    private String profileImageUrl;

    /* ========== 온보딩/소속(수정 가능) ========== */
    @Column(name = "university", nullable = false, length = 100)
    private String university;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type", nullable = false, length = 20)
    private UnitType unitType; // 총학/단과대/학과

    @Column(name = "unit_name", nullable = false, length = 50)
    private String unitName;

    /* ========== 권한/상태 ========== */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20, updatable = false)
    private Role role = Role.SECRETARY;

    @Builder.Default
    @Column(name = "onboarded", nullable = false)
    private boolean onboarded = false;

    /* ========== 운영 정보 ========== */
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "created_at", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false, nullable = false)
    private LocalDateTime updatedAt;

    /* ========== 도메인 메서드 ========== */

    /** 최초 온보딩 완료 처리 (이후 홈 진입) */
    public void completeOnboarding(String university, UnitType unitType, String unitName) {
        this.university = university;
        this.unitType = unitType;
        this.unitName = unitName;
        this.onboarded = true;
    }

    /** 소속 변경(학교/단위/단위명) – 이름/이미지는 수정 대상이 아님 */
    public void updateAffiliation(String university, UnitType unitType, String unitName) {
        this.university = university;
        this.unitType = unitType;
        this.unitName = unitName;
    }

    /** 로그인 성공 시각 갱신 */
    public void touchLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    /* ========== Enums ========== */
    public enum AuthProvider { KAKAO }
    public enum UnitType { STUDENT_COUNCIL, COLLEGE, DEPARTMENT }
    public enum Role { SECRETARY }
}

