package com.sws.sws.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InfoUpdateRequestDto {

    private String userName;
    private String nickname;

}
