package com.wedding.wedding_invitation.domain.member.repository;


import com.wedding.wedding_invitation.domain.member.entity.Member;
import com.wedding.wedding_invitation.global.config.JpaAuditConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(JpaAuditConfig.class)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("회원가입 : 데이터 DB 저장")
    void saveMemberTest() {
        // 1.준비  2.실행  3.검증 Given-when-then
        Member member = Member.builder()
                .username("testUser")
                .password("pw1")
                .name("테스트유저")
                .email("testUser@example.com")
                .emailAgreement(true)
                .phone("01012345678")
                .smsAgreement(false)
                .birth(LocalDate.of(2000,5,1))
                .zipCode("12345")
                .address("경기도")
                .addressDetail("용인시")
                .privacyAgreed(true)
                .build();

        Member saveMember = memberRepository.save(member);

        assertThat(saveMember.getId()).isNotNull(); // null 인지 확인
        assertThat(saveMember.getUsername()).isEqualTo("testUser");
        assertThat(saveMember.getPassword()).isEqualTo("pw1");
        assertThat(saveMember.getName()).isEqualTo("테스트유저");
        assertThat(saveMember.getEmail()).isEqualTo("testUser@example.com");
        assertThat(saveMember.isEmailAgreement()).isTrue();
        assertThat(saveMember.getPhone()).isEqualTo("01012345678");
        assertThat(saveMember.isSmsAgreement()).isFalse();
        assertThat(saveMember.getBirth()).isEqualTo(LocalDate.of(2000,5,1));
        assertThat(saveMember.getZipCode()).isEqualTo("12345");
        assertThat(saveMember.getAddress()).isEqualTo("경기도");
        assertThat(saveMember.getAddressDetail()).isEqualTo("용인시");
        assertThat(saveMember.isPrivacyAgreed()).isTrue();

        // Repository Test 회원가입 DB 저장 및 조회 검증 테스트 성공 

    }

}
