package com.wedding.wedding_invitation.domain.member.dto.request;

import com.wedding.wedding_invitation.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class MemberSignUpRequest {

    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private LocalDate birth;
    private String zipCode;
    private String address;
    private String addressDetail;

    // 동의 여부 (체크박스)
    private boolean emailAgreement;
    private boolean smsAgreement;
    private boolean privacyAgreed;


    public Member toEntity (String encodedPassword) {
        return Member.builder()
                .username(this.username)
                .password(encodedPassword)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .birth(this.birth)
                .zipCode(this.zipCode)
                .address(this.address)
                .addressDetail(this.addressDetail)
                .build();
    }

}
