package com.wedding.wedding_invitation.domain.member.dto.request;

import com.wedding.wedding_invitation.domain.member.entity.Member;
import com.wedding.wedding_invitation.domain.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpRequest {

    private String username;        // 로그인아이디, 중복불가
    private String password;        // 비밀번호
    private String name;            // 회원 이름
    private String email;           // 이메일, 중복불가
    private String phone;           // 연락처, 중복불가 13자리 제약
    private LocalDate birth;        // 생년월일
    private String zipCode;        // 우편번호, 5자리제한
    private String address;         // 주소
    private String addressDetail;  // 상세주소
    private boolean emailAgreement; // 이메일 알림 동의
    private boolean smsAgreement;   // SMS 알림 동의
    private boolean privacyAgreed;  // 약관 동의
    private MemberRole role;

    public Member toEntity(String encodePassword) {
        return Member.builder()
                .username(this.username)
                .password(encodePassword)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .birth(this.birth)
                .zipCode(this.zipCode)
                .address(this.address)
                .addressDetail(this.addressDetail)
                .emailAgreement(this.emailAgreement)
                .smsAgreement(this.smsAgreement)
                .privacyAgreed(this.privacyAgreed)
                .role(this.role)
                .build();
    }

}


