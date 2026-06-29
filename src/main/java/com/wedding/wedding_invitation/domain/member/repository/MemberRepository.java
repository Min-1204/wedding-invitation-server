package com.wedding.wedding_invitation.domain.member.repository;

import com.wedding.wedding_invitation.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 회원가입기능 ( 아이디중복확인, 연락처중복확인, 이메일확인 )
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);

    // 아이디찾기
    Optional<Member> findByNameAndEmail(String name, String email);

    // 비밀번호찾기
    Optional<Member> findByUsernameAndEmail(String username, String email);

    // 회원 찾기
    Optional<Member> findByUsername(String username);

}
