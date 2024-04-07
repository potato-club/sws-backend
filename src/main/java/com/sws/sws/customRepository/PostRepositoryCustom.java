package com.sws.sws.customRepository;

import com.sws.sws.enums.TagName;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryCustom {

    List<Long> findPostIdsByTags(List<TagName> tags);
}
