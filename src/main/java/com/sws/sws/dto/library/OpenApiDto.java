package com.sws.sws.dto.library;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OpenApiDto {

    private String libraryName;
    private String addr;
    private String openTime;
    private String TelNum;
    private double mapY;
    private double mapX;

    public LocationResponseDto toResponse() {
        return LocationResponseDto.builder()
                .libraryName(libraryName)
                .addr(addr)
                .openTime(openTime)
                .TelNum(TelNum)
                .mapX(mapX)
                .mapY(mapY)
                .build();
    }

}
