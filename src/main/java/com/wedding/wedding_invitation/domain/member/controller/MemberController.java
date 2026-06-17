package com.wedding.wedding_invitation.domain.member.controller;


import com.wedding.wedding_invitation.domain.member.dto.request.MemberSignUpRequest;
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
    @GetMapping("check-username")
    public ResponseEntity<String> checkUsername(@RequestParam String username) {
        return ResponseEntity.ok(memberService.checkUsername(username));
    }

    // 이메일중복확인
    @GetMapping("check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(memberService.checkEmail(email));
    }

    // 아이디찾기
    // 비밀번호찾기
    // 로그인
    // 로그아웃
    // 비밀번호 변경
    // 주소지 변경

}
