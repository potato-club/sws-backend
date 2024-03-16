package com.sws.sws.service;

import com.sws.sws.dto.post.*;
import com.sws.sws.entity.CategoryEntity;
import com.sws.sws.entity.PostEntity;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.error.ErrorCode;
import com.sws.sws.error.exception.BadRequestException;
import com.sws.sws.error.exception.CategoryNotFoundException;
import com.sws.sws.error.exception.PostNotFoundException;
import com.sws.sws.error.exception.UnAuthorizedException;
import com.sws.sws.repository.CategoryRepository;
import com.sws.sws.repository.PostRepository;
import com.sws.sws.utils.ResponseValue;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public ResponsePostListDto findAllPost() {

        List<PostEntity> all = postRepository.findTop5ByOrderByCreatedAtDesc();

        if (all.isEmpty()) {
            return ResponsePostListDto.builder()
                    .size(0)
                    .posts(Collections.emptyList())
                    .build();
        } else {

            List<ResponsePostDto> collect = all.stream()
                    .filter(Objects::nonNull)
                    .map(ResponseValue::getAllBuild)
                    .collect(Collectors.toList());

            return ResponsePostListDto.builder()
                    .size(all.size())
                    .posts(collect)
                    .build();
        }
    }

    public List<ResponsePostDto> findAllPostByLogic(Long lastPostId, int size) { // 좋아요순으로 변경하도록 추가


        PageRequest pageRequest = PageRequest.of(0, size);
        Page<PostEntity> entityPage = postRepository.findByIdLessThanOrderByIdDesc(lastPostId, pageRequest);
        List<PostEntity> postEntityList = postRepository.findAllOrderByLikesDesc();

        List<ResponsePostDto> responsePostDtos  = postEntityList.stream()
                .map(postEntity -> ResponsePostDto.builder()
                        .id(postEntity.getId())
                        .createdAt(postEntity.getCreatedAt())
                        .likeNums(postEntity.getLikes())
                        .views(postEntity.getViews())
                        .title(postEntity.getTitle())
                        .content(postEntity.getContent())
                        .categoryName(postEntity.getCategory().getName())
                        .build())
                .collect(Collectors.toList());
        return responsePostDtos;

    }

    public PaginationDto findPostByCategoryId(Long categoryId, Pageable pageable) {
        Optional<CategoryEntity> id = categoryRepository.findById(categoryId);

        if(id == null){
            throw  new BadRequestException("카테고리를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_EXCEPTION);
        }

        List<PostEntity> postList = id.stream()
                .flatMap(category -> category.getPosts().stream())
                .sorted(Comparator.comparing(PostEntity::getCreatedAt).reversed())
                .collect(Collectors.toList());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        int endItem = Math.min(startItem + pageSize, postList.size());

        List<PostEntity> pageContent = postList.subList(startItem, endItem);
        List<ResponsePostDto> postDtos = pageContent.stream()
                .map(ResponseValue::getAllBuild)
                .collect(Collectors.toList());

        long totalPages = (long) Math.ceil((double) postList.size() / (double) pageSize);
        boolean isLastPage = !pageable.isPaged() || currentPage >= totalPages - 1;

        return ResponseValue.getPaginationDto(totalPages, isLastPage, (long) postList.size(), postDtos);

    }




    public ResponsePostDto findOnePost(Long id) {
        Optional<PostEntity> postOptional = postRepository.findById(id);
        PostEntity post = postOptional.orElseThrow(() -> new NoSuchElementException("게시물이 존재하지 않습니다."));
        addPostView(id);
        return ResponseValue.getOneBuild(post);

    }

    public Long createPost(RequestPostDto requestDto, HttpServletRequest request) {

        Optional<UserEntity> user = userService.findByUserToken(request);
        if(user.get().getUserRole() == null) {
            throw new UnAuthorizedException("로그인후 이용해주세요.", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        } else {
            CategoryEntity category = categoryRepository.findByName(requestDto.getCategory())
                    .orElseThrow(() -> new CategoryNotFoundException("카테고리가 존재하지 않습니다.", ErrorCode.CATEGORY_NOT_FOUND_EXCEPTION));

            PostEntity post = PostEntity.builder()
                    .title(requestDto.getTitle())
                    .userEntity(user.get())
                    .content(requestDto.getContent())
                    .category(category)
                    .build();

            PostEntity savedPost = postRepository.save(post); // 게시물 저장하고

            return savedPost.getId(); // 게시물 id값 반환
        }
    }

    public Long updatePost(RequestUpdatePostDto updatePostDto, Long id, HttpServletRequest request) {

        Optional<UserEntity> user = userService.findByUserToken(request);
        if(user.get().getUserRole() == null) {
            throw new UnAuthorizedException("로그인후 이용해주세요.", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        } else {

            PostEntity originPost = postRepository.findById(id).
                    orElseThrow(() -> new PostNotFoundException("게시물이 존재하지 않습니다.", ErrorCode.POST_NOT_FOUND_EXCEPTION));

            String updatedTitle = updatePostDto.getTitle();
            String updatedContent = updatePostDto.getContent();

            originPost.updatePost(updatedTitle, updatedContent);
            return postRepository.save(originPost).getId();
        }
    }

    public void deletePost(Long id, HttpServletRequest request) {

        Optional<UserEntity> user = userService.findByUserToken(request);
        if(user.get().getUserRole() == null) {
            throw new UnAuthorizedException("로그인후 이용해주세요.", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        } else {

            PostEntity originPost = postRepository.findById(id).
                    orElseThrow(() -> new PostNotFoundException("게시물이 존재하지 않습니다.", ErrorCode.POST_NOT_FOUND_EXCEPTION));

            postRepository.deleteById(originPost.getId());
        }
    }

    public PostEntity getPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시물이 존재하지 않습니다.", ErrorCode.POST_NOT_FOUND_EXCEPTION));
    }

    public void addPostView(Long id) {
        Optional<PostEntity> post = postRepository.findById(id);
        if (post.isPresent()) {
            PostEntity postEntity = post.get();
            postEntity.increaseViews();
            postRepository.save(postEntity);
        } else {
            throw new BadRequestException("잘못된 접근입니다.", ErrorCode.ACCESS_DENIED_EXCEPTION);
        }
    }


}
