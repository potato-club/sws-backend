package com.sws.sws.dto.library;

import com.sws.sws.entity.LibraryEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LibraryDto {

    private String libraryName;
    private String addr;
    private String openTime;
    private String TelNum;
    private double mapX;
    private double mapY;

    @Builder
    public LibraryDto(String libraryName, String addr, String openTime, String TelNum, double mapX, double mapY) {
        this.libraryName = libraryName;
        this.addr = addr;
        this.openTime = openTime;
        this.TelNum = TelNum;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public LibraryEntity toEntity() {
        return LibraryEntity.builder()
                .libraryName(libraryName)
                .addr(addr)
                .openTime(openTime)
                .TelNum(TelNum)
                .mapX(mapX)
                .mapY(mapY)
                .build();
    }
}
