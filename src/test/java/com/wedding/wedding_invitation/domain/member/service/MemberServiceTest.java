package com.wedding.wedding_invitation.domain.member.service;

import com.wedding.wedding_invitation.domain.member.dto.request.MemberSignUpRequest;
import com.wedding.wedding_invitation.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DisplayName("회원 서비스 JUnit 테스트")
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    // 진짜 DB 안 쓰고 Mock 으로 만든 Repository

    @Mock
    private PasswordEncoder passwordEncoder;
    // 가짜로 만든 PasswordEncoder

    @InjectMocks
    private MemberService memberService;
    // Inject 주입하다. MemberService에 Mock 으로 지정한 PasswordEncoder와 MemberRepository를 주입.
    // 여기서 Mock 가짜로 만든것을 주입한다.


    // Member Entity 생성
    private Member createMemberEntity() {
        return Member.builder()
                .name("홍길동")
                .username("testUser")
                .password("1234")
                .phone("010-1234-5678")
                .birth(LocalDate.of(2000,1,1))
                .email("test@email.com")
                .zipCode("12345")
                .address("경기도")
                .addressDetail("용인")
                .emailAgreement(true)
                .smsAgreement(false)
                .privacyAgreed(true)
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
    @DisplayName("회원가입 성공 테스트")
    void signUp_Success_Test() {
        MemberSignUpRequest request = createMember();

        // given DB 작동 로직 입력
        given(memberRepository.existsByUsername(anyString())).willReturn(false);
        given(memberRepository.existsByEmail(anyString())).willReturn(false);
        given(memberRepository.existsByPhone(anyString())).willReturn(false);


        // when (실행)  MemberService의 signUp(회원가입비즈니스로직) 호출
        memberService.signUp(request);


        // then (검증)
        // 여기서 verify(객체).메서드() => 이 객체의 메서드가 실행되었는가?
        // 즉, signUp의 MemberRepository.save가 실제로 호출되어 저장 로직까지 작동했는지 테스트
        verify(memberRepository).save(any());
        // 호출 시 테스트 성공
        // 호출 안되었을 시 테스트 실패

    }

    @Test
    @DisplayName("회원가입 실패 - 중복아이디 테스트")
    void signUp_False_Username_Duplication() {

        MemberSignUpRequest request = createMember(); // 멤버 생성 메서드

        // given
        given(memberRepository.existsByUsername(anyString())).willReturn(true);
        // 설계 -> 회원아이디가 존재해? -> 존재한다고 설계 입력

        // when
        assertThrows(IllegalArgumentException.class, () -> memberService.signUp(request));
        // 실행 -> memberService의 signUp 메서드를 실행됬을때, IllegalArgumentException 예외가 발생해야해.

        // then
        verify(memberRepository, never()).save(any());
        // 결과 -> 실행결과에 save가 실행되면 안돼
        
        // 회원아이디가 중복이면 예외가 발생되고, save는 호출되면안된다 멈춰야하기때문에
        // memberRepository 에서 save가 never() 호출되면 안돼
    }

}
