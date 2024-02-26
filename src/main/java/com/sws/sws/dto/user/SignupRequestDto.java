package com.sws.sws.dto.user;

import com.sws.sws.entity.UserEntity;
import com.sws.sws.enums.UserRole;
import lombok.Data;

@Data

public class SignupRequestDto {
    private String email;

    private String password;

    private String userName;

    private String nickname;

    private UserRole userRole;

    public UserEntity toEntity() {

        return UserEntity.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .nickname(nickname)
                .userRole(userRole)
                .build();
    }



}
