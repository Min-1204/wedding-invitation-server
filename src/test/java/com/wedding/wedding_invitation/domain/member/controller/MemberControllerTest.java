package com.wedding.wedding_invitation.domain.member.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedding.wedding_invitation.domain.member.dto.request.*;
import com.wedding.wedding_invitation.domain.member.entity.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest // Spring 전체 테스트
@AutoConfigureMockMvc // MockMvc  MockMvc 자동 설정 → HTTP 요청 테스트용
@Transactional // 테스트 코드 실행 후 DB 초기화
@DisplayName("Member 통합테스트")
public class MemberControllerTest {

    @Autowired // 필드 자동 주입
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MemberSignUpRequest createSignUpUser () {
        return MemberSignUpRequest.builder()
                .username("testUser")
                .name("홍길동")
                .password("1234")
                .birth(LocalDate.of(2000, 1, 1))
                .phone("010-1234-5678")
                .email("test@email.com")
                .zipCode("12345")
                .address("경기도")
                .addressDetail("용인")
                .emailAgreement(true)
                .smsAgreement(true)
                .privacyAgreed(true)
                .role(MemberRole.ROLE_USER)
                .build();
    }

    private MemberFindUsernameRequest createFindUsername () {
        return MemberFindUsernameRequest.builder()
                .name("홍길동")
                .email("test@email.com")
                .build();
    }

    private MemberFindPasswordRequest createFindPassword () {
        return MemberFindPasswordRequest.builder()
                .username("testUser")
                .email("test@email.com")
                .build();
    }

    private MemberLoginRequest createLoginUser () {
        return MemberLoginRequest.builder()
                .username("testUser")
                .password("1234")
                .build();
    }

    private MemberChangePasswordRequest createChangePassword () {
        return MemberChangePasswordRequest.builder()
                .username("testUser")
                .currentPassword("1234")
                .newPassword("4321")
                .build();
    }
    
    private MemberChangeAddressRequest createChangeAddress () {
        return MemberChangeAddressRequest.builder()
                .username("testUser")
                .zipCode("54321")
                .address("서울시")
                .addressDetail("종로구")
                .build();
    }


    // 회원가입 성공 / 실패
    @Test
    @DisplayName("회원가입 테스트 - 성공")
    void signUp_Test() throws Exception {
        MemberSignUpRequest signUpRequest = createSignUpUser();

        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 테스트 - 실패")
    void signUp_Fail_Test() throws Exception {
        MemberSignUpRequest signUpRequest = createSignUpUser();

        mockMvc.perform(post("/api/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest());
    }


    // 아이디 중복확인 성공 / 실패
    @Test
    @DisplayName("아이디 중복확인 테스트 - 사용가능")
    void Duplication_Username_Test() throws Exception {
//      아이디가 DB에 있는지 없는지 확인하는 기능
//      성공하려면? DB에 해당 아이디가 없어야 사용가능
//      실패하려면? DB에 해당 아이디가 있어야 사용불가능 -> 회원가입으로 DB에 아이디 데이터를 입력해야함
//      어떤 데이터가 필요한가? DB에 확인할 username이 있어야한다.

        mockMvc.perform(get("/api/members/check-username")
                .param("username","testUser"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("아이디 중복확인 테스트 - 이미사용중")
    void Duplication_Fail_Username_Test() throws Exception {
        MemberSignUpRequest signUpRequest = createSignUpUser();

        mockMvc.perform(post("/api/members/signup") // Post URL
                .contentType(MediaType.APPLICATION_JSON) // HTTP Body의 Type은 JSON Type
                .content(objectMapper.writeValueAsString(signUpRequest))) // newUser Java객체를 문자열로 변환해서 Body에 담아 전달
                        .andExpect(status().isOk()); // 상태코드가 200이면 검증 성공.


        mockMvc.perform(get("/api/members/check-username") // get URL
                .param("username","testUser")) // param username은 변수, testUser 값
                .andExpect(status().isBadRequest());
    }

//    ContentType = 데이터의 형식
//    예시 => contentType(MediaType.APPLICATION_JSON)
//    Content = Body 데이터
//    예시 => content(objectMapper.writeValueAsString(newUser)) // 보내는 데이터


//    1. 이 기능이 뭘 하는 기능인가?
//    2. 성공하려면 어떤 상황이어야 하는가?
//    3. 실패하려면 어떤 상황이어야 하는가?
//    4. 어떤 데이터가 필요한가?



    // 이메일 중복확인 성공 / 실패
    @Test
    @DisplayName("이메일 중복확인 테스트 - 사용가능")
    void Duplication_Email_Test() throws Exception {
        mockMvc.perform(get("/api/members/check-email")
                .param("email","test@email.com"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이메일 중복확인 테스트 - 이미사용중")
    void Duplication_Fail_Email_Test() throws Exception {
        MemberSignUpRequest signUpRequest = createSignUpUser();

        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/members/check-email")
                .param("email", "test@email.com"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("이미 사용중인 이메일입니다."));


    }


    // 아이디 찾기 성공 / 실패
    @Test
    @DisplayName("아이디 찾기 테스트 - 성공")
    void findBy_Username_Test() throws Exception {

        MemberSignUpRequest signUpUser = createSignUpUser();
        MemberFindUsernameRequest findUsernameRequest = createFindUsername();

        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpUser)))
                        .andExpect(status().isOk());

        mockMvc.perform(post("/api/members/find-username")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(findUsernameRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("testUser"));
    }

    @Test
    @DisplayName("아이디 찾기 테스트 - 없는 아이디")
    void findBy_Fail_Username_Test() throws Exception {

        MemberFindUsernameRequest findUsernameRequest = createFindUsername();
        mockMvc.perform(post("/api/members/find-username")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(findUsernameRequest)))
                .andExpect(status().isBadRequest());
    }

    // 비밀번호 찾기 성공 / 실패
    @Test
    @DisplayName("비밀번호 찾기 테스트 - 성공")
    void find_Password_Test() throws Exception {
        MemberSignUpRequest signUpUser = createSignUpUser();
        MemberFindPasswordRequest findPasswordRequest = createFindPassword();

        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpUser)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/members/find-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(findPasswordRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 찾기 테스트 - 없는 회원")
    void find_Fail_Password_Test() throws Exception {
        MemberFindPasswordRequest findPasswordRequest = createFindPassword();

        mockMvc.perform(post("/api/members/find-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(findPasswordRequest)))
                .andExpect(status().isBadRequest());
    }


    // 로그인 성공 / 실패
    @Test
    @DisplayName("로그인 테스트 - 성공")
    void login_Test() throws Exception {
        MemberSignUpRequest signUpUser = createSignUpUser();
        MemberLoginRequest loginRequest = createLoginUser();

        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpUser)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 테스트 - 실패")
    void Login_Fail_Test() throws Exception {
        MemberLoginRequest loginUser = createLoginUser();

        mockMvc.perform(post("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isBadRequest());
    }

    // 비밀번호 변경 성공 / 실패
    @Test
    @DisplayName("비밀번호 변경 테스트 - 성공")
    void change_Password_Test () throws Exception {
        MemberSignUpRequest signUpRequest = createSignUpUser();
        MemberChangePasswordRequest changePasswordRequest = createChangePassword();

        mockMvc.perform(post("/api/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(patch("/api/members/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("비밀번호 변경 성공"));
    }

    @Test
    @DisplayName("비밀번호 변경 테스트 - 실패")
    void fail_Change_Password_Test() throws Exception {

        MemberChangePasswordRequest changePasswordRequest = createChangePassword();

        mockMvc.perform(patch("/api/members/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest());

    }


    // 주소지 변경 성공 / 실패
    @Test
    @DisplayName("주소지 변경 테스트 - 성공")
    void change_Address_Test() throws Exception {

        MemberSignUpRequest signUpRequest = createSignUpUser();
        MemberChangeAddressRequest changeAddressRequest = createChangeAddress();

        mockMvc.perform(post("/api/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        mockMvc.perform(patch("/api/members/change-address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeAddressRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("주소가 변경되었습니다."));
    }

    @Test
    @DisplayName("주소지 변경 테스트 - 실패")
    void fail_Change_Address_Test() throws Exception {

        MemberChangeAddressRequest changeAddressRequest = createChangeAddress();

        mockMvc.perform(patch("/api/members/change-address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeAddressRequest)))
                .andExpect(status().isBadRequest());
    }

}
