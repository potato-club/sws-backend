package com.sws.sws.dto.user;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;

    private String password;

    private String userName;

    private String nickname;



}
