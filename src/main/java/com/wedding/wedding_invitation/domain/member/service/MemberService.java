package com.wedding.wedding_invitation.domain.member.service;

import com.wedding.wedding_invitation.domain.member.dto.request.MemberSignUpRequest;
import com.wedding.wedding_invitation.domain.member.entity.Member;
import com.wedding.wedding_invitation.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 아이디 중복확인
    public String checkUsername(String username) {
        if (memberRepository.existsByUsername(username)) {
            return "이미 사용중인 아이디 입니다.";
        }
        return "사용 가능한 아이디 입니다.";
    }

    // 이메일 중복 확인
    public String checkEmail(String email) {
        if(memberRepository.existsByEmail(email)) {
            return "이미 사용중인 이메일 입니다.";
        }
        return "사용 가능한 이메일 입니다.";
    }

    public void signUp(MemberSignUpRequest request) {

        if(memberRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }
        if (memberRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("이미 사용중인 연락처 입니다.");
        }

        memberRepository.save(request.toEntity(passwordEncoder.encode(request.getPassword())));


    }

    // 아이디찾기
    // 비밀번호찾기
    // 로그인
    // 로그아웃
    // 비밀번호 변경
    // 주소지 변경

}
