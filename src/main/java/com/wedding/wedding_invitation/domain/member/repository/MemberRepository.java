package com.wedding.wedding_invitation.domain.member.repository;

import com.wedding.wedding_invitation.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 회원가입기능 ( 아이디중복확인, 연락처중복확인, 이메일확인 )
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);

    // 아이디찾기
    Optional<Member> findByNameAndEmail(String name, String email);

    // 비밀번호찾기
    Optional<Member> findByUsernameAndEmail(String username, String email);

<<<<<<< Updated upstream
=======
    // 회원 찾기
    Optional<Member> findByUsername(String username);

>>>>>>> Stashed changes
    // 로그인 (검증)
    // 로그아웃 (실행)
    // 비밀번호 변경 (검증 , 실행)
    // 주소지 변경 (실행)
}
