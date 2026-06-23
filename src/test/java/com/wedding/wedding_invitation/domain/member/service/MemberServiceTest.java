package com.wedding.wedding_invitation.domain.member.service;

<<<<<<< Updated upstream
=======
import com.wedding.wedding_invitation.domain.member.dto.request.MemberChangeAddressRequest;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberChangePasswordRequest;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberLoginRequest;
>>>>>>> Stashed changes
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

    private MemberSignUpRequest createMember() {
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
                .build();
    }

    private MemberChangeAddressRequest createMemberChangeAddressRequest() {
        return MemberChangeAddressRequest.builder()
                .username("testUser")
                .zipCode("12345")
                .address("경기도")
                .addressDetail("용인")
                .build();
    }

    // 아이디 중복확인
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

    // 아이디 찾기 성공
    @Test
    @DisplayName("아이디 찾기 테스트 - 성공")
    void find_Username_Test() {
        Member member = createMemberEntity();

        given(memberRepository.findByNameAndEmail(anyString(), anyString())).willReturn(Optional.of(member));

        String result = memberService.findUsername("홍길동", "test@email.com");

        assertThat(result).isEqualTo("testUser");
    }

    // 아이디 찾기 실패
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
        given(memberRepository.findByUsernameAndEmail(anyString(),anyString())).willReturn(Optional.of(member));
        String result = memberService.findPassword("testUser", "test@email.com");
        assertThat(result).isEqualTo(member.getPassword());
    }

    @Test
    @DisplayName("비밀번호 찾기 테스트 - 존재하지 않는 회원")
    void find_Password_Fail_Test() {
        given(memberRepository.findByUsernameAndEmail(anyString(),anyString())).willReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.findPassword("testUser", "test@email.com"));
        System.out.println("예외 발생 메세지: " + exception.getMessage());
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
<<<<<<< Updated upstream
    void signUp_Success_Test() {
        MemberSignUpRequest request = createMember();
=======
    void signUp_Test() {
        MemberSignUpRequest request = createSignUpRequest();
>>>>>>> Stashed changes

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
    void signUp_False_Username_Duplication_Test() {

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
<<<<<<< Updated upstream
=======

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
    void login_Fail_Username_Test () {
        MemberLoginRequest loginRequest = createMemberLoginRequest();

        given(memberRepository.findByUsername(anyString())).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class , () -> memberService.login(loginRequest));

    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void login_Fail_Password_Test () {
        Member member = createMemberEntity();
        MemberLoginRequest loginRequest = createMemberLoginRequest();

        given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(loginRequest.getPassword(),member.getPassword())).willReturn(false);

        assertThrows(IllegalArgumentException.class, () -> memberService.login(loginRequest));

    }


    @Test
    @DisplayName("비밀번호변경 테스트 - 성공")
    void change_Password_Test () {

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

    // 주소지 변경 성공
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

    // 주소지 변경 실패
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
>>>>>>> Stashed changes

}
