package com.sws.sws.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Level {
    LEVEL1("LEV1", 0, 10),
    LEVEL2("LEV2",10, 20),
    LEVEL3("LEV3",20, 30),
    LEVEL4("LEV4",30, 40),
    LEVEL5("LEV5",40, 50);

    private final String title;
    private final int minLikes;
    private final int maxLikes;

    public static Level getDefault() {
        return LEVEL1;
    }


}
