package com.sws.sws.repository;

import com.sws.sws.entity.CommentEntity;
import com.sws.sws.entity.LikeEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    LikeEntity findByUserAndPost(UserEntity user, PostEntity post);
    LikeEntity findByUserAndComment(UserEntity user, CommentEntity comment);


}
