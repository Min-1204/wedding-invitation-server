package com.wedding.wedding_invitation.domain.member.service;

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
    // 진짜 DB 안 쓰고 Mock 으로 만든 Repository

    @Mock
    private PasswordEncoder passwordEncoder;
    // 가짜로 만든 PasswordEncoder

    @InjectMocks
    private MemberService memberService;
    // Inject 주입하다. MemberService에 Mock 으로 지정한 PasswordEncoder와 MemberRepository를 주입.
    // 여기서 Mock 가짜로 만든것을 주입한다.

    private MemberSignUpRequest createSignUpRequest() {
       return  MemberSignUpRequest.builder()
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
                .role(MemberRole.ROLE_USER)
                .build();
    }

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
                .role(MemberRole.ROLE_USER)
                .build();
    }

    private MemberLoginRequest createMemberLoginRequest() {
        return MemberLoginRequest.builder()
                .username("testUser")
                .password("1234")
                .build();
    }

    private MemberChangePasswordRequest createMemberChangePasswordRequest() {
        return MemberChangePasswordRequest.builder()
                .username("testUser")
                .currentPassword("1234")
                .newPassword("4321")
                .build();
    }


    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUp_Success_Test() {
        MemberSignUpRequest request = createSignUpRequest();

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

        MemberSignUpRequest request = createSignUpRequest(); // 멤버 생성 메서드

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
    
    
    // 로그인, 비밀번호변경 테스트해야함.
    @Test
    @DisplayName("로그인 성공")
    void login_Test() {

        Member member = createMemberEntity();
        // 멤버 생성
        MemberLoginRequest request = createMemberLoginRequest();
        // 로그인 멤버변수 생성

        // given 설계
        given(memberRepository.findByUsername("testUser")).willReturn(Optional.of(member));
        given(passwordEncoder.matches("1234",  member.getPassword())).willReturn(true);

        // when 실행
        MemberLoginResponse response = memberService.login(request); // 객체전달


        // then 결과
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("testUser");
        assertThat(response.getRole()).isEqualTo(MemberRole.ROLE_USER);

    }

    @Test
    @DisplayName("로그인 실패 - 존재하지않는 아이디")
    void login_Fail_Username () {
        MemberLoginRequest loginRequest = createMemberLoginRequest();

        given(memberRepository.findByUsername(anyString())).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class , () -> memberService.login(loginRequest));

    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void login_Fail_Password () {
        Member member = createMemberEntity();
        MemberLoginRequest loginRequest = createMemberLoginRequest();

        given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(loginRequest.getPassword(),member.getPassword())).willReturn(false);

        assertThrows(IllegalArgumentException.class, () -> memberService.login(loginRequest));

    }


    @Test
    @DisplayName("비밀번호변경 테스트 - 성공")
    void change_Password_Test () {
//        비밀번호를 변경하려는 회원이 존재 하는가?
//        비밀번호를 변경하려면 현재 비밀번호가 맞는지 확인해야하고
//        입력한 비밀번호가 맞다면 변경하고싶은 비밀번호를 입력해
//        그 비밀번호를 저장하고, 저장 완료 반환


        Member member = createMemberEntity(); // 변경할 회원
        MemberChangePasswordRequest request = createMemberChangePasswordRequest(); // 변경요청할 회원

        given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(request.getCurrentPassword(),member.getPassword())).willReturn(true);


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

    // 주소지 변경 테스튼

}
