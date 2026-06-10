package com.wedding.wedding_invitation.domain.member.repository;

import com.wedding.wedding_invitation.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 회원가입기능 ( 아이디중복확인, 연락처중복확인, 이메일확인 )
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);

    // 아이디찾기기능
    // 비밀번호찾기기능
    // 로그인기능
    // 로그아웃기능
    // 개인정보변경기능
}
