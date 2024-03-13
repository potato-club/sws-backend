package com.sws.sws.dto.user;

import com.sws.sws.enums.Level;
import com.sws.sws.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MyPageDto {
    private String email;
    private String userName;
    private String nickname;
    private Level level;
    private UserRole userRole;


}
