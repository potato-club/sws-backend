package com.sws.sws.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class LibraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_id")
    private Long id;
    private String libraryName;
    private String addr;
    private String openTime;
    private String TelNum; // 전화번호

    @Column(name = "map_x")
    private double mapX; // 위도

    @Column(name = "map_y")
    private double mapY; // 경도

    public LibraryEntity(Long id, String libraryName, String addr, String openTime, String TelNum, double mapX, double mapY){
        this.id = id;
        this.libraryName = libraryName;
        this.addr = addr;
        this.openTime = openTime;
        this.TelNum = TelNum;
        this.mapX = mapX;
        this.mapY = mapY;
    }


}
