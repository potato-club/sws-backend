package com.sws.sws.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;


@Data
@Builder
@AllArgsConstructor
public class FileDto {
    @Schema(description = "이미지 파일 이름")
    private String fileName;
    @Schema(description = "이미지 파일 url")
    private String fileUrl;


}
