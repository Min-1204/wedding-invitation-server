package com.wedding.wedding_invitation.domain.member.repository;

import com.wedding.wedding_invitation.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByNameAndEmail(String name, String email);
    Optional<Member> findByPasswordAndEmail(String username, String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    // JpaRepository를 상속 → 기본 도구 제공
    // < Entity 명, PK 타입 >
    // Member 데이터를 DB에 저장, 수정, 삭제, 조회 ( CRUD ) 제공
}
