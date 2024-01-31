package com.sws.sws.dto.user;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String userEmail;
    private String password;
}
