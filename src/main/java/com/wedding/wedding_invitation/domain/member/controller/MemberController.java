package com.wedding.wedding_invitation.domain.member.controller;


import com.wedding.wedding_invitation.domain.member.dto.request.MemberChangePasswordRequest;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberLoginRequest;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberSignUpRequest;
import com.wedding.wedding_invitation.domain.member.dto.response.MemberLoginResponse;
import com.wedding.wedding_invitation.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody MemberSignUpRequest request) {
        memberService.signUp(request);
        return ResponseEntity.ok("회원가입이 완료 되었습니다.");
    }

    // 아이디중복확인
    @GetMapping("/check-username")
    public ResponseEntity<String> checkUsername(@RequestParam String username) {
        return ResponseEntity.ok(memberService.checkUsername(username));
    }

    // 이메일중복확인
    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(memberService.checkEmail(email));
    }

    // 아이디찾기
    @GetMapping("/find-username")
    public ResponseEntity<String> findUsername(@RequestParam String name, String email){
        String username = memberService.findUsername(name, email);
        return ResponseEntity.ok(username);
    }

    // 비밀번호찾기
    @GetMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestParam String username, String email) {
        String password = memberService.findPassword(username, email);
        return ResponseEntity.ok(password);
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login (@RequestBody MemberLoginRequest request) {
        MemberLoginResponse result = memberService.login(request);
        return ResponseEntity.ok(result);
    }


    // 로그아웃


    // 비밀번호 변경
    @PatchMapping("change-password")
    public ResponseEntity<String> changePassword(@RequestBody MemberChangePasswordRequest request) {
        String result = memberService.changePassword(request);

        return ResponseEntity.ok(result);
    }


    // 주소지 변경

}
