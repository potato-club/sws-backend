package com.sws.sws.repository;

import com.sws.sws.entity.TagEntity;
import com.sws.sws.enums.TagName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    List<TagEntity> findByTagIn(List<TagName> tags);

}