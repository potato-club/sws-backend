package com.sws.sws.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Level {
    LEVEL1("level1", 0, 10),
    LEVEL2("level2",10, 20),
    LEVEL3("level3",20, 30),
    LEVEL4("level4",30, 40),
    LEVEL5("level5",40, 50);

    private final String title;
    private final int minLikes;
    private final int maxLikes;

}
