package com.sws.sws.dto.user;

import lombok.Data;

@Data
public class LoginResponseDto {
    private boolean success;
    private String message;
    private String accessToken;
    private String refreshToken;

    public LoginResponseDto(boolean success, String message, String accessToken, String refreshToken) {
        this.success = success;
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
