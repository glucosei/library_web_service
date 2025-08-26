package com.library.security;

import com.library.domain.enums.Status;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT 토큰을 생성, 토큰에서 인증 정보 추출, 토큰 유효성 검증을 담당하는 클래스.
 */
@Component
public class JwtTokenProvider {

    private final Key key;
    private final long tokenValidityInMilliseconds;

    /**
     * application.properties에서 JWT 관련 속성을 주입받아 초기화합니다.
     * @param secretKey JWT 서명에 사용할 비밀 키
     * @param tokenValidityInSeconds 토큰의 유효 시간 (초)
     */
    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey,
                            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    /**
     * 사용자명과 권한을 기반으로 JWT 토큰을 생성합니다.
     * @param username 사용자명 (username)
     * @param status 사용자의 상태 (권한으로 사용)
     * @return 생성된 JWT
     */
    public String createToken(String username, Status status) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", Collections.singletonList(status.name()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 토큰에서 인증 정보를 가져와 Spring Security의 Authentication 객체로 변환합니다.
     * @param token JWT
     * @return Authentication 객체
     */
    public Authentication getAuthentication(String token) {
        // 토큰을 파싱하여 클레임(claims)을 추출합니다.
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        List<String> authorities = (List<String>) claims.get("auth");

        // 클레임의 정보를 기반으로 UserDetails 객체를 생성합니다.
        UserDetails principal = new User(claims.getSubject(), "",
                authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));

        // UserDetails와 토큰, 권한 정보를 담아 Authentication 객체를 반환합니다.
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    /**
     * JWT 토큰의 유효성을 검증합니다.
     * @param token 검증할 JWT
     * @return 유효성 여부 (true/false)
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.err.println("Invalid JWT signature: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Expired JWT token: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Unsupported JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}