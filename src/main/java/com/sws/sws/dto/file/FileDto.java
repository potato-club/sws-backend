package com.sws.sws.dto.file;

import com.sws.sws.entity.FileEntity;
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

    public FileDto(FileEntity fileEntity) {
        this.fileName = fileEntity.getFileName();
        this.fileUrl = fileEntity.getFileUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof FileDto))
            return false;

        FileDto fileDto = (FileDto) o;

        return Objects.equals(this.fileName, fileDto.getFileName()) && Objects.equals(this.fileUrl, fileDto.getFileUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fileName, this.fileUrl);
    }
}
