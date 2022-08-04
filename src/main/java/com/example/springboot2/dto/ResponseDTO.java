package com.example.springboot2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@Builder
public class ResponseDTO implements Serializable {
    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public static ResponseDTO setSuccess(String msg) {
        return ResponseDTO.builder()
                .success(true)
                .msg(msg)
                .build();
    }

    public static ResponseDTO setFail(int code, String msg) {
        return ResponseDTO.builder()
                .success(false)
                .code(code)
                .msg(msg)
                .build();
    }

}
