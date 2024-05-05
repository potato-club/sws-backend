package com.sws.sws.repository;

import com.sws.sws.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findAll();

    // 특정 파일 조회
    FileEntity findByFileName(String fileName);

    // 특정 사용자가 올린 파일 조회
    List<FileEntity> findByUserEntityUserId(Long userId);

    // 특정 사용자가 올린 파일 삭제
    void deleteByUserEntityUserId(Long userId);
}

