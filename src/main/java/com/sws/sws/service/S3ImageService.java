package com.sws.sws.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sws.sws.dto.file.FileRequestDto;
import com.sws.sws.dto.file.FileResponseDto;
import com.sws.sws.entity.FileEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

    @Transactional
    public List<FileEntity> updateFiles(Object entity, List<MultipartFile> files, List<FileRequestDto> requestDto) throws IOException {
        List<FileEntity> fileList = new ArrayList<>();
        Class<?> entityType = entity.getClass();

        if (entityType.equals(PostEntity.class)) {
            fileList = fileRepository.findByPostEntity((PostEntity) entity);
        } else if (entityType.equals(UserEntity.class)) {
            fileList = fileRepository.findByUserEntity((UserEntity) entity);
        }

        // 새로 업로드된 파일 리스트와 기존 파일 리스트를 비교하여 업로드된 파일 중 기존 파일 삭제
        List<FileEntity> uploadedFiles = existsFiles(files);
        for (FileEntity uploadedFile : uploadedFiles) {
            for (FileEntity existingFile : fileList) {
                if (uploadedFile.getFileName().equals(existingFile.getFileName())) {
                    s3Client.deleteObject(bucketName, existingFile.getFileName()); // S3에서 파일 삭제
                    fileRepository.delete(existingFile); // 데이터베이스에서 파일 엔티티 삭제
                    fileList.remove(existingFile); // 리스트에서도 삭제
                    break;
                }
            }
        }

        // 삭제되지 않은 기존 파일들은 결과 리스트에 추가
        List<FileEntity> resultList = new ArrayList<>(fileList);

        // 새로 업로드된 파일들을 엔티티와 연결하고 결과 리스트에 추가
        for (FileEntity uploadedFile : uploadedFiles) {
            if (entityType.equals(PostEntity.class)) {
                uploadedFile.setPostEntity((PostEntity) entity);
            } else if (entityType.equals(UserEntity.class)) {
                uploadedFile.setUserEntity((UserEntity) entity);
            }
            resultList.add(fileRepository.save(uploadedFile));
        }


        return resultList;
    }


    @Transactional
    public void deleteFile(String fileName) {
        FileEntity fileEntity = fileRepository.findByFileName(fileName);
        if (fileEntity != null) {
            // S3에서 파일 삭제
            s3Client.deleteObject(bucketName, fileName);

            // 파일 엔티티의 삭제 여부를 표시하고 저장
            fileEntity.checkDeleted();
            fileRepository.save(fileEntity);
        }
    }
    public List<FileEntity> existsFiles(List<MultipartFile> files) throws IOException {
        List<FileEntity> fileList = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String key = UUID.randomUUID().toString() + "-" + originalFilename;

            if (s3Client.doesObjectExist(bucketName, key)) {
                continue; // 이미 존재하면 스킵
            }

            InputStream inputStream = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());

            // S3에 파일 업로드
            s3Client.putObject(new PutObjectRequest(bucketName, key, inputStream, metadata));

            // 파일 엔티티 생성
            FileEntity fileEntity = FileEntity.builder()
                    .fileName(originalFilename)
                    .fileUrl(s3Client.getUrl(bucketName, key).toString())
                    .build();

            // 파일 엔티티 저장
            fileList.add(fileRepository.save(fileEntity));
        }

        return fileList;
    }

    //캐러셀 api 추가
    @Transactional
    public FileResponseDto uploadCarousel(MultipartFile multipartFile) throws IOException {
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
        FileEntity savedFile = fileRepository.save(file);

        return new FileResponseDto(savedFile.getId(), fileUrl);
    }
}