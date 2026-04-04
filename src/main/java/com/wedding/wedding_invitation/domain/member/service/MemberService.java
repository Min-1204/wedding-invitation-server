package com.wedding.wedding_invitation.domain.member.service;

import com.sun.jdi.request.DuplicateRequestException;
import com.wedding.wedding_invitation.domain.member.dto.request.MemberSignUpRequest;
import com.wedding.wedding_invitation.domain.member.entity.Member;
import com.wedding.wedding_invitation.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    // 필요한 기능 생각하기 ( 요구사항 정의서, 기능명세서의 필요성이 여기서 확인된다. )
    // 해당 기능들에 대한 흐름을 나열해보는것도 좋은거 같다.


    // 회원가입 기능 ( 회원가입을 하려면 회원 정보를 DB에 저장해야한다. )
    public void signup (MemberSignUpRequest request) {

        // 유효성 검사 로직 필요 정적메서드
        validateDuplicateMember(request);

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        memberRepository.save(request.toEntity(encodedPassword));
        log.info("회원가입 완료 {}", request.getUsername());
    }

//    // 아이디 중복확인
//    public boolean isUserDuplicate(String username) {
//        log.info("Service 중복확인 기능 실행 : {}", username + "은 사용중인 아이디 입니다.");
//        return memberRepository.existsByUsername(username);
//    }

    // 로그인 ( 로그인을 하려면 아이디와 비밀번호를 DB에서 조회하고 검증해야한다 )

//    public Member login(String username, String password) {
//
//        log.info("로그인 시도: {}", username);
//
//        // 아이디 조회 시작.
//        Member member = memberRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
//
//        // 비밀번호 검증 PasswordEncoder 추가해서 검증 메서드 matches 활용
//        if(!member.getPassword().equals(password)) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
//        }
//
//        // 로그인 성공 처리 로직
//        log.info("로그인 성공 : {}", username);
//
//        return member;
//    }
//
//    // 아이디찾기 ( 아이디를 찾으려면 이름과 이메일을 DB에서 조회하고 검증한 뒤 이메일인증번호를 발송하고, 인증번호를 검증해야한다)
//    public String findUsername(String name, String email) {
//
//        // 회원 조냊여부 확인
//        Member member = memberRepository.findByNameAndEmail(name,email)
//                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
//        return member.getUsername();
//    }
//
//    // 비밀번호 찾기
//    public String findPassword(String username, String email) {
//        Member member = memberRepository.findByPasswordAndEmail(username, email)
//                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
//        return member.getUsername();
//    }


    // 비밀번호 변경


    private void validateDuplicateMember(MemberSignUpRequest request) {
        if(memberRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }
        if(memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다");
        }
        if(memberRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("이미 사용중인 연락처 입니다.");
        }
    }



}
