package com.wedding.wedding_invitation.domain.member.service;

import com.wedding.wedding_invitation.domain.member.dto.request.MemberChangeAddressRequest;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberChangePasswordRequest;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberLoginRequest;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberSignUpRequest;
import com.wedding.wedding_invitation.domain.member.dto.response.MemberLoginResponse;
import com.wedding.wedding_invitation.domain.member.entity.Member;
import com.wedding.wedding_invitation.domain.member.entity.MemberRole;
import com.wedding.wedding_invitation.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("회원 서비스 JUnit 테스트")
public class MemberServiceTest {

    private static final Logger log = LoggerFactory.getLogger(MemberServiceTest.class);

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;


    // Member Entity 생성
    private Member createMemberEntity() {
        return Member.builder()
                .name("홍길동")
                .username("testUser")
                .password("1234")
                .phone("010-1234-5678")
                .birth(LocalDate.of(2000, 1, 1))
                .email("test@email.com")
                .zipCode("12345")
                .address("경기도")
                .addressDetail("용인")
                .emailAgreement(true)
                .smsAgreement(false)
                .privacyAgreed(true)
                .role(MemberRole.ROLE_USER)
                .build();
    }

    // Member 회원가입 DTO 생성
    private MemberSignUpRequest createSignUpRequest() {
        return MemberSignUpRequest.builder()
                .name("홍길동")
                .username("testUser")
                .password("1234")
                .phone("010-1234-5678")
                .birth(LocalDate.of(2000, 1, 1))
                .email("test@email.com")
                .zipCode("12345")
                .address("경기도")
                .addressDetail("용인")
                .emailAgreement(true)
                .smsAgreement(false)
                .privacyAgreed(true)
                .role(MemberRole.ROLE_USER)
                .build();
    }



    // Member 로그인 DTO 생성
    private MemberLoginRequest createMemberLoginRequest() {
        return MemberLoginRequest.builder()
                .username("testUser")
                .password("1234")
                .build();
    }


    // Member 비밀번호변경 DTO 생성
    private MemberChangePasswordRequest createMemberChangePasswordRequest() {
        return MemberChangePasswordRequest.builder()
                .username("testUser")
                .currentPassword("1234")
                .newPassword("4321")
                .build();
    }


    // Member 주소변경 DTO 생성
    private MemberChangeAddressRequest createMemberChangeAddressRequest() {
        return MemberChangeAddressRequest.builder()
                .username("testUser")
                .zipCode("12345")
                .address("경기도")
                .addressDetail("용인")
                .build();
    }

//    ===============================   테스트 로직 라인   ===============================

    @Test
    @DisplayName("아이디 중복 확인 테스트 - 사용 가능")
    void duplication_Username_Test() {
        given(memberRepository.existsByUsername(anyString())).willReturn(false);
        String result = memberService.checkUsername("testUser");
        assertThat(result).isEqualTo("사용 가능한 아이디 입니다.");
    }

    @Test
    @DisplayName("아이디 중복 확인 테스트 - 이미 사용중")
    void duplication_Username_Fail_Test() {
        given(memberRepository.existsByUsername(anyString())).willReturn(true);
        String result = memberService.checkUsername("testUser");
        assertThat(result).isEqualTo("이미 사용중인 아이디 입니다.");
    }

    @Test
    @DisplayName("이메일 중복 확인 테스트 - 사용 가능")
    void duplication_Email_Test() {
        given(memberRepository.existsByEmail(anyString())).willReturn(false);
        String result = memberService.checkEmail("test@email.com");
        assertThat(result).isEqualTo("사용 가능한 이메일 입니다.");
    }

    @Test
    @DisplayName("이메일 중복 확인 테스트 - 이미 사용중")
    void duplication_Email_Fail_Test() {
        given(memberRepository.existsByEmail(anyString())).willReturn(true);
        String result = memberService.checkEmail("test@email.com");
        assertThat(result).isEqualTo("이미 사용중인 이메일 입니다.");
    }

    @Test
    @DisplayName("아이디 찾기 테스트 - 성공")
    void find_Username_Test() {
        Member member = createMemberEntity();
        given(memberRepository.findByNameAndEmail(anyString(), anyString())).willReturn(Optional.of(member));
        String result = memberService.findUsername("홍길동", "test@email.com");
        assertThat(result).isEqualTo("testUser");
    }

    @Test
    @DisplayName("아이디 찾기 테스트 - 존재하지 않는 아이디")
    void find_Username_Fail_Test() {
        given(memberRepository.findByNameAndEmail(anyString(), anyString())).willReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.findUsername("강감찬", "Test@email.com"));
        System.out.println("발생한 예외 메시지: " + exception.getMessage());
    }

    @Test
    @DisplayName("비밀번호 찾기 테스트 - 성공")
    void find_Password_Test() {
        Member member = createMemberEntity();
        given(memberRepository.findByUsernameAndEmail(anyString(), anyString())).willReturn(Optional.of(member));
        String result = memberService.findPassword("testUser", "test@email.com");
        assertThat(result).isEqualTo(member.getPassword());
    }

    @Test
    @DisplayName("비밀번호 찾기 테스트 - 존재하지 않는 회원")
    void find_Password_Fail_Test() {
        given(memberRepository.findByUsernameAndEmail(anyString(), anyString())).willReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.findPassword("testUser", "test@email.com"));
        System.out.println("예외 발생 메세지: " + exception.getMessage());
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUp_Test() {
        MemberSignUpRequest request = createSignUpRequest();
        given(memberRepository.existsByUsername(anyString())).willReturn(false);
        given(memberRepository.existsByEmail(anyString())).willReturn(false);
        given(memberRepository.existsByPhone(anyString())).willReturn(false);
        memberService.signUp(request);
        verify(memberRepository).save(any());
    }

    @Test
    @DisplayName("회원가입 실패 테스트")
    void signUp_False_Username_Duplication_Test() {
        MemberSignUpRequest request = createSignUpRequest();
        given(memberRepository.existsByUsername(anyString())).willReturn(true);
        assertThrows(IllegalArgumentException.class, () -> memberService.signUp(request));
        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("로그인 성공")
    void login_Test() {
        Member member = createMemberEntity();
        MemberLoginRequest request = createMemberLoginRequest();
        given(memberRepository.findByUsername("testUser")).willReturn(Optional.of(member));
        given(passwordEncoder.matches("1234", member.getPassword())).willReturn(true);
        MemberLoginResponse response = memberService.login(request);
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("testUser");
        assertThat(response.getRole()).isEqualTo(MemberRole.ROLE_USER);
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지않는 아이디")
    void login_Fail_Username_Test() {
        MemberLoginRequest loginRequest = createMemberLoginRequest();
        given(memberRepository.findByUsername(anyString())).willReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> memberService.login(loginRequest));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void login_Fail_Password_Test() {
        Member member = createMemberEntity();
        MemberLoginRequest loginRequest = createMemberLoginRequest();
        given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())).willReturn(false);
        assertThrows(IllegalArgumentException.class, () -> memberService.login(loginRequest));
    }

    @Test
    @DisplayName("비밀번호변경 테스트 - 성공")
    void change_Password_Test() {
        Member member = createMemberEntity();
        MemberChangePasswordRequest request = createMemberChangePasswordRequest();
        given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(request.getCurrentPassword(), member.getPassword())).willReturn(true);
        String result = memberService.changePassword(request);
        verify(memberRepository).save(any());
        assertThat(result).isEqualTo("비밀번호 변경 성공");
        log.info(result);
    }

    @Test
    @DisplayName("비밀번호변경 테스트 - 실패")
    void change_Password_Fail_Test() {
        Member member = createMemberEntity();
        MemberChangePasswordRequest changePasswordRequest = createMemberChangePasswordRequest();
        given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), member.getPassword())).willReturn(false);
        assertThrows(IllegalArgumentException.class, () -> memberService.changePassword(changePasswordRequest));
    }

    @Test
    @DisplayName("주소지 변경 테스트 - 성공")
    void address_Change_Test() {
        Member member = createMemberEntity();
        MemberChangeAddressRequest request = createMemberChangeAddressRequest();
        given(memberRepository.findByUsername(anyString())).willReturn(Optional.of(member));
        String result = memberService.changeAddress(request);
        verify(memberRepository).save(any());
        assertThat(result).isEqualTo("주소가 변경되었습니다.");
    }

    @Test
    @DisplayName("주소지 변경 테스트 - 실패")
    void address_Change_Fail_Test() {
        MemberChangeAddressRequest request = createMemberChangeAddressRequest();
        given(memberRepository.findByUsername(anyString())).willReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.changeAddress(request));
        verify(memberRepository, never()).save(any());
        System.out.println("발생한 예외 메시지: " + exception.getMessage());
    }
}