package com.wedding.wedding_invitation.domain.member.service;


import com.wedding.wedding_invitation.domain.member.dto.request.MemberSignUpRequest;
import com.wedding.wedding_invitation.domain.member.entity.Member;
import com.wedding.wedding_invitation.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("회원 서비스 테스트")
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    //      MemberRepository의 가짜 객체를 만들어줌
    //     * 실제 DB에 접근하지 않고, 우리가 정의한 동작만 한다.

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;
    //      MemberService 객체를 생성하고
    //      위 Mock으로 만든 memberRepository를 자동으로 주입해줌
    //      의존성 주입 해준다고 생각하면 된다.


    //회원가입 메서드
    private Member createTestMember() {
        return Member.builder()
                .username("testUser")
                .password("pw1")
                .name("테스트유저")
                .birth(LocalDate.of(1995, 5, 20))
                .email("testUser@example.com")
                .emailAgreement(true)
                .phone("01012345678")
                .smsAgreement(false)
                .privacyAgreed(true)
                .zipCode("12345")
                .address("경기도")
                .addressDetail("용인시")
//                .createdAt(LocalDateTime.now())
                .build();
    }

    //회원가입 메서드
    private MemberSignUpRequest createSignUpRequest() {
        return MemberSignUpRequest.builder()
                .username("testUser")
                .password("pw1")
                .name("테스트유저")
                .birth(LocalDate.of(1995, 5, 20))
                .email("testUser@example.com")
                .emailAgreement(true)
                .phone("01012345678")
                .smsAgreement(false)
                .privacyAgreed(true)
                .zipCode("12345")
                .address("경기도")
                .addressDetail("용인시")
                .build();
    }


        @Test
        @DisplayName("회원가입 성공 테스트")
        void signUp_Success() {
             MemberSignUpRequest request = createSignUpRequest();

            given(memberRepository.existsByUsername(anyString())).willReturn(false);
            given(memberRepository.existsByEmail(anyString())).willReturn(false);
            given(memberRepository.existsByPhone(anyString())).willReturn(false);

            memberService.signup(request);

            verify(memberRepository).save(any());

        }

        @Test
        @DisplayName("회원가입 실패 : 중복된 아이디")
        void signUp_DuplicateUsername() {
            MemberSignUpRequest request = createSignUpRequest();

            given(memberRepository.existsByUsername(anyString())).willReturn(true);

            assertThrows(IllegalArgumentException.class, () -> memberService.signup(request));

            verify(memberRepository, never()).save(any());
        }

        @Test
        @DisplayName("회원가입 실패 : 중복된 이메일")
        void signUp_DuplicateEmail() {
            MemberSignUpRequest request = createSignUpRequest();

            given(memberRepository.existsByUsername(anyString())).willReturn(false);
            given(memberRepository.existsByEmail(anyString())).willReturn(true);

            assertThrows(IllegalArgumentException.class, () -> memberService.signup(request));

            verify(memberRepository, never()).save(any());

        }

    @Test
    @DisplayName("회원가입 실패 : 중복된 연락처")
    void signUp_DuplicatePhone() {
        MemberSignUpRequest request = createSignUpRequest();

        given(memberRepository.existsByUsername(anyString())).willReturn(false);
        given(memberRepository.existsByEmail(anyString())).willReturn(false);
        given(memberRepository.existsByPhone(anyString())).willReturn(true);

        assertThrows(IllegalArgumentException.class, () -> memberService.signup(request));

        verify(memberRepository, never()).save(any());

    }


    }


    // 로그인

    // 아이디 찾기

    // 비밀번호 찾기


