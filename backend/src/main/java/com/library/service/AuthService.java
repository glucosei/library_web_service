package com.library.service;

import com.library.domain.Member;
import com.library.domain.enums.Role;
import com.library.domain.enums.Status;
import com.library.dto.SignUpRequest;
import com.library.repository.MemberRepository;
import com.library.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 인증 및 회원 정보 관련 비즈니스 로직을 처리하는 서비스.
 * UserDetailsService를 구현하여 Spring Security의 사용자 정보 로딩에 사용됩니다.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * Spring Security에서 사용자명(username)을 기반으로 회원 정보를 로드하는 메서드.
     * UserDetailsService 인터페이스의 필수 구현 메서드입니다.
     *
     * @param username 로그인 시 입력된 사용자명
     * @return UserDetails 객체 (여기서는 Member 엔티티)
     * @throws UsernameNotFoundException 해당 사용자가 존재하지 않을 경우
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username을 사용하여 회원 정보 조회
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 id: " + username));
    }

    /**
     * 로그인 요청을 처리하고 JWT 토큰을 발급하는 비즈니스 로직.
     *
     * @param username 로그인 시 입력된 사용자명
     * @param password 로그인 시 입력된 비밀번호
     * @return 생성된 JWT 토큰
     */
    @Transactional
    public String login(String username, String password) {
        // loadUserByUsername을 호출하여 사용자가 존재하는지 확인하고 UserDetails 객체를 가져옵니다.
        UserDetails userDetails = loadUserByUsername(username);

        // 입력된 비밀번호를 암호화된 비밀번호와 비교합니다.
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new IllegalArgumentException("유효하지 않은 비밀번호");
        }

        // 인증 성공 시 JWT 토큰 생성 및 반환
        // Member 객체를 UserDetails로 형변환하여 Status 필드에 접근합니다.
        Member member = (Member) userDetails;
        return jwtTokenProvider.createToken(member.getUsername(), member.getStatus());
    }
    /**
     * 회원가입 요청을 처리하고 새로운 회원을 생성합니다.
     *
     * @param request 회원가입에 필요한 사용자 정보를 담고 있는 DTO
     * @return 저장된 Member 엔티티
     * @throws IllegalArgumentException 사용자명이 이미 존재할 경우
     */
    @Transactional
    public Member signUp(SignUpRequest request) {
        // 1. 사용자명 중복 체크
        if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 같은 id가 존재합니다");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. DTO를 엔티티로 변환
        Member newMember = Member.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .name(request.getName())
                .nickname(request.getNickname())
                .phoneNumber(request.getPhoneNumber())
                .gender(null) // DTO에서 받은 성별 정보로 매핑
                .status(Status.ACTIVE) // 기본 상태는 ACTIVE
                .role(Role.ROLE_MEMBER) // 기본 역할은 ROLE_MEMBER
                .build();

        // 4. 엔티티 저장
        return memberRepository.save(newMember);
    }
}