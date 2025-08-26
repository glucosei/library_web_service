package com.library.repository;

import com.library.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Member 엔티티의 데이터 접근을 위한 리포지토리.
 * JpaRepository를 상속받아 기본 CRUD 메서드와 사용자 정의 쿼리를 제공합니다.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * username으로 회원을 조회합니다.
     * @param username 조회할 사용자명
     * @return Optional<Member> 회원 정보가 있으면 Member 객체를, 없으면 Optional.empty()를 반환
     */
    Optional<Member> findByUsername(String username);
}