package com.wedding.wedding_invitation.domain.member.repository;


import com.wedding.wedding_invitation.domain.member.entity.Member;
import com.wedding.wedding_invitation.global.config.JpaAuditConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditConfig.class) // 👈 Audit 설정을 테스트 환경으로 강제 주입
public class MemberRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(MemberRepositoryTest.class);

    @Autowired
    private MemberRepository memberRepository;

    // 테스트용 Member생성 Test
    private Member createMemberTest() {
        return Member.builder()
                .name("홍길동")
                .username("testUser")
                .password("1234")
                .phone("010-1234-5678")
                .email("test@email.com")
                .zipCode("12345")
                .birth(LocalDate.of(2000,1,1))
                .address("경기도 용인")
                .addressDetail("로또빌라")
                .emailAgreement(true)
                .smsAgreement(true)
                .privacyAgreed(false)
                .build();
    }


    // DB 회원 저장
    @Test
    @DisplayName("회원 저장 테스트")
    void saveMemberTest() {
        Member member = createMemberTest();

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getPhone()).isNotNull();
        assertThat(savedMember.getName()).isEqualTo("홍길동");
    }


    // DB 아이디 조회
    @Test
    @DisplayName("아이디 중복확인 테스트 -> True 반환")
    void existsByUsernameTest() {

        Member member = createMemberTest();
        memberRepository.save(member);
        boolean testUser = memberRepository.existsByUsername("testUser");
        boolean testEmail = memberRepository.existsByEmail("test@email.com");
        boolean testPhone = memberRepository.existsByPhone("010-1234-5678");
        log.info("아이디 중복확인 테스트결과 {}", testUser);

        assertThat(testUser).isTrue();
        assertThat(testEmail).isTrue();
        assertThat(testPhone).isTrue();

    }

    // DB 아이디 조회
    @Test
    @DisplayName("없는아이디 중복확인 테스트 -> False 반환")
    void existsByUsernameTest_False() {
        Member member = createMemberTest();
        memberRepository.save(member);
        boolean testUser = memberRepository.existsByUsername("test");
        boolean testEmail = memberRepository.existsByEmail("testEmail@test.com");
        boolean testPhone = memberRepository.existsByPhone("010-1111-4444");
        log.info("없는아이디 중복확인 테스트결과 {}", testUser);

        assertThat(testUser).isFalse();
        assertThat(testEmail).isFalse();
        assertThat(testPhone).isFalse();

    }


//    회원 찾기
    @Test
    @DisplayName("회원 테스트 - 존재하지 않는 회원")
    void findByUser_False_Test () {
        Member member = createMemberTest();
        memberRepository.save(member);
        Optional<Member> findUser = memberRepository.findByUsername("testUser1");
        assertThat(findUser).isEmpty();
        log.info("없는 회원 테스트 결과 {}", findUser);
    }

//    아이디 찾기 테스트
    @Test
    @DisplayName("아이디 찾기 테스트 - 성공")
    void findByUsername_Test() {
        Member member = createMemberTest();
        memberRepository.save(member);
        Member savedUser = memberRepository.findByNameAndEmail(member.getName(), member.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));
        assertThat(savedUser.getUsername()).isEqualTo("testUser");
        log.info("아이디 찾기 성공 테스트 결과: {}", savedUser.getUsername());
    }

    @Test
    @DisplayName("아이디 찾기 테스트 - 존재하지 않는 아이디")
    void findByUsername_Fail_Test() {
        Member member = createMemberTest();
        memberRepository.save(member);
        Optional<Member> savedUser = memberRepository.findByNameAndEmail("testUser1", "test@emaill.com");
        assertThat(savedUser).isEmpty();
        log.info("아이디 찾기 실패 테스트 결과 : {}", savedUser.isEmpty());
    }


//    비밀번호 찾기 테스트

    @Test
    @DisplayName("비밀번호 찾기 테스트 - 성공")
    void findPassword_Test() {
        Member member = createMemberTest();
        memberRepository.save(member);
        Optional<Member> savedUser = memberRepository.findByUsernameAndEmail("testUser", "test@email.com");
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getPassword()).isEqualTo("1234");
        log.info("비밀번호 찾기 성공 테스트 결과 : {} ", savedUser.isPresent());

    }

    @Test
    @DisplayName("비밀번호 찾기 테스트 - 존재하지 않는 회원")
    void findPassword_Fail_Test(){
        Member member = createMemberTest();
        memberRepository.save(member);
        Optional<Member> savedUser = memberRepository.findByUsernameAndEmail("notFoundUser", "notUser@email.com");
        assertThat(savedUser).isEmpty();
        log.info("비밀번호 찾기 실패 테스트 결과: {}", savedUser.isEmpty());
    }
}
