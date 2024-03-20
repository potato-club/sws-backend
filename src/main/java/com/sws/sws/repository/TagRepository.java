package com.sws.sws.repository;

import com.sws.sws.entity.TagEntity;
import com.sws.sws.enums.TagName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    List<TagEntity> findByTagIn(List<TagName> tags);

}