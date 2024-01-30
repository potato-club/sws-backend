package com.sws.sws.repository;

import com.sws.sws.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository  extends JpaRepository<PostEntity, Long> {
}
