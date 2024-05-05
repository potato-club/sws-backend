package com.sws.sws.controller;

import com.sws.sws.entity.FileEntity;
import com.sws.sws.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/s3")
@Tag(name = "AWS S3 upload Controller", description = "S3 업로드 API")
public class S3Controller {

    private final S3ImageService s3ImageService;

    @Operation(summary = "S3 Upload API")
    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileEntity uploadedFile = s3ImageService.uploadFile(file);
            return ResponseEntity.ok(uploadedFile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
