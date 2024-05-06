package com.sws.sws.dto.library;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LocationResponseDto {

    private String libraryName;
    private String addr;
    private String openTime;
    private String TelNum;
    private double mapY;
    private double mapX;

}
