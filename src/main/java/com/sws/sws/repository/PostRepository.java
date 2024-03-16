package com.sws.sws.repository;

import com.sws.sws.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findTop5ByOrderByCreatedAtDesc();

    @Query("SELECT p FROM PostEntity p ORDER BY p.likes DESC")
    List<PostEntity> findAllOrderByLikesDesc();
    Page<PostEntity> findByIdLessThanOrderByIdDesc(Long lastPostId, PageRequest pageRequest);



}
