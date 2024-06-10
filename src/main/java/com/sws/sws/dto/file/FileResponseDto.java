package com.sws.sws.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDto {
    //캐러셀
    private Long id;
    private String fileUrl;
}
