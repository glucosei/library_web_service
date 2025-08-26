package com.library.dto;

import lombok.Data;

/**
 * 클라이언트에서 서버로 로그인 요청 시 사용되는 DTO.
 * 사용자의 아이디와 비밀번호를 담고 있습니다.
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
