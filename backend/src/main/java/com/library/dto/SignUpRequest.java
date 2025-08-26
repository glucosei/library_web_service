package com.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {
    private String username;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String gender; // Gender Enum으로 변환 필요
}