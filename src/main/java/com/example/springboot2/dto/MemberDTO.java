package com.example.springboot2.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberDTO {
    @Getter
    @Setter
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
