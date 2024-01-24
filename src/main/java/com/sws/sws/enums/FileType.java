package com.sws.sws.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum FileType {
    IMAGE("IMAGE", "이미지 파일"),
    VIDEO("VIDEO", "비디오 파일"),
    PDF("PDF", "PDF 파일");

    private final String key;
    private final String title;
}
