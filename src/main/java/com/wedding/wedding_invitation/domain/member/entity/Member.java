package com.wedding.wedding_invitation.domain.member.entity;


import com.wedding.wedding_invitation.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity // JPA 사용으로 Data Base와 1:1로 매핑할 수 있게 해주는 어노테이션
@Table(name = "members") // DB의 테이블 이름을 직접 설정할 수 있는 어노테이션
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 회원고유번호, 자동생성

    @Column(nullable = false, unique = true)
    private String username;        // 로그인아이디, 중복불가

    @Column(nullable = false)
    private String password;        // 비밀번호

    @Column(nullable = false, length = 50)
    private String name;            // 회원 이름

    @Column(nullable = false, unique = true)
    private String email;           // 이메일, 중복불가

    @Column(nullable = false, unique = true, length = 13)
    private String phone;           // 연락처, 중복불가 13자리 제약

    @Column(nullable = false, length = 8)
    private LocalDate birth;        // 생년월일

    @Column(nullable = false, length = 5)
    private String zipCode;        // 우편번호, 5자리제한

    @Column(nullable = false, length = 255)
    private String address;         // 주소

    @Column(nullable = false, length = 255)
    private String addressDetail;  // 상세주소

    @Column(nullable = false)
    private boolean emailAgreement; // 이메일 알림 동의

    @Column(nullable = false)
    private boolean smsAgreement;   // SMS 알림 동의

    @Column(nullable = false)
    private boolean privacyAgreed;  // 약관 동의

    private LocalDateTime deletedAt; // 탈퇴일자

    @Enumerated(EnumType.STRING)
    private MemberRole role;


    public void changePassword(String encodePassword) {
        this.password = encodePassword;
    }
    public void changeAddress(String zipCode, String address, String addressDetail) {
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
    }

}
