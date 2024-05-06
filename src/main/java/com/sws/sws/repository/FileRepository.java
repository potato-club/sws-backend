package com.sws.sws.repository;

import com.sws.sws.entity.FileEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findAll();

    FileEntity findByFileName(String fileName);

    List<FileEntity> findByPostEntity(PostEntity postEntity);
    List<FileEntity> findByUserEntity(UserEntity userEntity);


}


