package com.wedding.wedding_invitation.domain.member.service;

import com.wedding.wedding_invitation.domain.member.dto.request.MemberChangeAddressRequest;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberChangePasswordRequest;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberLoginRequest;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberSignUpRequest;
import com.wedding.wedding_invitation.domain.member.dto.response.MemberLoginResponse;
import com.wedding.wedding_invitation.domain.member.entity.Member;
import com.wedding.wedding_invitation.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

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


    // 아이디 찾기
    public String findUsername(String name, String email) {
        Member user = memberRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));
        String findUser = user.getUsername();
        return findUser;
    }

    // 비밀번호 찾기
    public String findPassword(String username, String email) {
        Member user = memberRepository.findByUsernameAndEmail(username, email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));
        String findUser = user.getPassword();
        return findUser;
    }

    // 아이디찾기
    // 비밀번호찾기
    // 로그인
    public MemberLoginResponse login(MemberLoginRequest request) {
        
        // 회원 존재 확인
        Member user = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));

        // 회원 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공
        return new MemberLoginResponse(user.getId(),user.getUsername(),user.getRole());
    }
    // 단일 책임 분리 ---> 별도 검증 및 회원존재 유효성 클래스 도입 검토 필요



    // 비밀번호 변경
    public String changePassword(MemberChangePasswordRequest request) {
        Member user = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

                if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                    throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                }

                user.changePassword(passwordEncoder.encode(request.getNewPassword()));

                memberRepository.save(user);

                return "비밀번호 변경 성공";

    }


    // 주소지 변경
    public String changeAddress (MemberChangeAddressRequest request) {

        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

        member.changeAddress(request.getZipCode(), request.getAddress(), request.getAddressDetail());

        memberRepository.save(member);

        return "주소가 변경되었습니다.";

    }

    // 비밀번호 변경
    public String changePassword(MemberChangePasswordRequest request) {
        Member user = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

                if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                    throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                }

                user.changePassword(passwordEncoder.encode(request.getNewPassword()));

                memberRepository.save(user);

                return "비밀번호 변경 성공";

    }


    // 주소지 변경
    // 로그아웃
}
