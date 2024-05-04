package com.sws.sws.dto.file;
import com.sws.sws.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
public class FileRequestDto {
    @Schema(description = "파일 이름")
    private String fileName;

    @Schema(description = "파일 Url")
    private String fileUrl;

    @Schema(description = "파일 삭제/교체 여부")
    private boolean deleted;

    public FileRequestDto(FileEntity fileEntity) {
        this.fileName = fileEntity.getFileName();
        this.fileUrl = fileEntity.getFileUrl();
        this.deleted = true;
    }

    public FileRequestDto(FileDto dto, boolean deleted) {
        this.fileName = dto.getFileName();
        this.fileUrl = dto.getFileUrl();
        this.deleted = deleted;
    }
}
