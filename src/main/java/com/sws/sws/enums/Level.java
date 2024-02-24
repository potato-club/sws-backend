package com.sws.sws.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Level {
    LEV1("LEV1", 0, 10),
    LEV2("LEV2",10, 20),
    LEV3("LEV3",20, 30),
    LEV4("LEV4",30, 40),
    LEV5("LEV5",40, 50);

    private final String title;
    private final int minLikes;
    private final int maxLikes;


}
