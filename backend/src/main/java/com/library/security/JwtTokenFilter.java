package com.library.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청 헤더에서 JWT 토큰을 추출합니다.
        String token = resolveToken(request);

        // 추출한 토큰이 유효한지 검증합니다.
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면, 토큰에서 인증 정보를 가져옵니다.
            Authentication auth = jwtTokenProvider.getAuthentication(token);

            // Spring Security의 SecurityContext에 인증 정보를 설정합니다.
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 다음 필터로 요청을 전달합니다.
        filterChain.doFilter(request, response);
    }


    /**
     * HTTP 요청 헤더에서 "Bearer" 토큰을 추출하는 헬퍼 메서드.
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 부분을 제외한 순수 토큰 값 반환
        }
        return null;
    }
}
