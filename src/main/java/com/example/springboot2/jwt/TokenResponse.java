package com.example.springboot2.jwt;

import lombok.Builder;
import lombok.Data;

@Builder
@Data// @Data 하면 getter setter  까지
public class TokenResponse {
    String accessToken;
    String refreshToken;
}
