package com.sws.sws.dto.user;

import com.sws.sws.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class InfoResponseDto {
    private String userName;
    private String nickname;
    private Level level;


}
