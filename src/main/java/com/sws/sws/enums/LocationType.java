package com.sws.sws.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LocationType {
    SUBWAY_STATION("지하철역", "지하철역"),
    HOUSE("집", "집"),
    SCHOOL("학교", "학교"),
    CAFE("카페", "카페");

    private final String key;
    private final String title;
}
