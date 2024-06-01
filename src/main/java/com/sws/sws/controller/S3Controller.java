package com.sws.sws.controller;

import com.sws.sws.dto.file.FileRequestDto;
import com.sws.sws.dto.file.FileResponseDto;
import com.sws.sws.entity.FileEntity;
import com.sws.sws.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/s3")
@Tag(name = "AWS S3 upload Controller", description = "S3 API")
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

    @Operation(summary = "Carousel S3 Upload API")
    @PostMapping("/carousel")
    public ResponseEntity<FileResponseDto> uploadCarousel(@RequestParam("file") MultipartFile file) {
        try {
            FileResponseDto fileResponseDto = s3ImageService.uploadCarousel(file);
            return ResponseEntity.ok(fileResponseDto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Update Files API")
    @PutMapping("/update-files")
    public ResponseEntity<List<FileEntity>> updateFiles(@RequestBody Object entity, @RequestParam("files") List<MultipartFile> files, @RequestBody List<FileRequestDto> requestDto) {
        try {
            List<FileEntity> updatedFiles = s3ImageService.updateFiles(entity, files, requestDto);
            return ResponseEntity.ok(updatedFiles);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Operation(summary = "S3 Check Exist Files API")
    @PostMapping("/check-files")
    public ResponseEntity<List<FileEntity>> checkExistFiles(@RequestParam("files") List<MultipartFile> files) {
        try {
            List<FileEntity> existingFiles = s3ImageService.existsFiles(files);
            return ResponseEntity.ok(existingFiles);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Delete File API")
    @DeleteMapping("/delete-file")
    public ResponseEntity<Void> deleteFile(@RequestParam("fileName") String fileName) {
        try {
            s3ImageService.deleteFile(fileName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
