package com.example.user_web_service.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String gameToken;
}
