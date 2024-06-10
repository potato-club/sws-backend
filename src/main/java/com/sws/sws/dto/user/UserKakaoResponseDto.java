package com.sws.sws.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserKakaoResponseDto {
    @Schema(description = "Email")
    private String email;

    @Schema(description = "Nickname")
    private String nickname;

    @Schema(description = "응답 코드", example = "200_OK / 201_CREATED")
    private String responseCode;

    @Schema(description = "액세스 토큰")
    private String accessToken;

    @Schema(description = "리프레시 토큰")
    private String refreshToken;


}
