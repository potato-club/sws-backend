package com.sws.sws.entity;

import com.sws.sws.dto.CreatePostRequestDto;
import com.sws.sws.dto.CreatePostResponseDto;
import com.sws.sws.repository.UserRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    @ManyToOne
    private CategoryEntity childCategory;

    @OneToMany(mappedBy = "postEntity")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "postEntity")
    private List<LikeEntity> likes;

    @OneToMany(mappedBy = "postEntity")
    private List<FileEntity> files;

    @OneToMany(mappedBy = "postEntity")
    private List<PostTagEntity> postTags;

    @Column(nullable = false, unique = false)
    private String title;

    @Column(nullable = false, unique = false)
    private String content;

    @Column(nullable = false, unique = false)
    private int views;

    @Column(nullable = false, unique = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, unique = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false, unique = false)
    private Boolean isDel;



}
