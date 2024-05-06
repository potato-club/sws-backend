package com.sws.sws.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sws.sws.entity.FileEntity;
import com.sws.sws.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Component
public class S3ImageService {
    private final AmazonS3Client s3Client;
    private final FileRepository fileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    @Transactional
    public FileEntity uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();
        String fileUrl = bucketName + "/" + fileName;
        long fileSize = multipartFile.getSize();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileSize);
        metadata.setContentType(multipartFile.getContentType());
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, multipartFile.getInputStream(), metadata));

        FileEntity file = FileEntity.builder()
                .fileName(fileName)
                .fileUrl(fileUrl)
                .size(fileSize)
                .deleted(false)
                .build();
        return fileRepository.save(file);
    }
}