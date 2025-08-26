package com.library.controller;

import com.library.dto.SignUpRequest;
import com.library.dto.LoginRequest;
import com.library.dto.TokenResponse;
import com.library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 인증(로그인) 관련 요청을 처리하는 컨트롤러.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    /**
     * 사용자 로그인 요청을 처리하는 API 엔드포인트.
     * 프론트엔드에서 username과 password를 포함한 JSON 데이터를 받아 인증을 시도합니다.
     *
     * @param loginRequest 로그인에 필요한 사용자명(username)과 비밀번호(password)를 담은 DTO
     * @return 로그인 성공 시 JWT 토큰을 담은 TokenResponse와 함께 200 OK 응답 반환
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest){
        String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
