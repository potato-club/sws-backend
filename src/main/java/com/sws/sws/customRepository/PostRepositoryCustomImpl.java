package com.sws.sws.customRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.QPostEntity;
import com.sws.sws.entity.QTagEntity;
import com.sws.sws.enums.TagName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Long> findPostIdsByTags(List<TagName> tags) {
        QPostEntity qPost = QPostEntity.postEntity;
        QTagEntity qTag = QTagEntity.tagEntity;

        return jpaQueryFactory
                .select(qPost.id)
                .from(qPost)
                .innerJoin(qPost.tags, qTag)
                .where(qTag.tag.in(tags))
                .distinct()
                .fetch();
    }

}
