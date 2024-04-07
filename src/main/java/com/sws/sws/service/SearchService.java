package com.sws.sws.service;

import com.sws.sws.customRepository.PostRepositoryCustom;
import com.sws.sws.dto.post.PaginationDto;
import com.sws.sws.dto.post.ResponsePostDto;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.enums.TagName;
import com.sws.sws.repository.PostRepository;
import com.sws.sws.utils.ResponseValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    private final PostRepositoryCustom postRepositoryCustom;
    private final PostRepository postRepository;

    public PaginationDto searchPostByTags(List<TagName> tags, Pageable pageable) {
        List<Long> ids = postRepositoryCustom.findPostIdsByTags(tags); // 태그가 있는 게시물 id값들 반환
        Page<PostEntity> postEntityPage = postRepository.findByIdIn(ids, pageable);

        List<ResponsePostDto> searchPost = postEntityPage.stream()
                .map(ResponseValue::getAllBuild)
                .collect(Collectors.toList());

        PaginationDto paginationDto = new PaginationDto();
        paginationDto.setTotalPage((long) postEntityPage.getTotalPages());
        paginationDto.setLastPage(postEntityPage.isLast());
        paginationDto.setTotalElement(postEntityPage.getTotalElements());
        paginationDto.setCategoryList(searchPost);

        return paginationDto;
    }
}
