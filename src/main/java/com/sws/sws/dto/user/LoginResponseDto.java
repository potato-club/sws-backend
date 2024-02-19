package com.sws.sws.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String responseCode;

}
