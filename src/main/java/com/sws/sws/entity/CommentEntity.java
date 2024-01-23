package com.sws.sws.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name="post_id")
    private PostEntity postEntity;

    @OneToMany(mappedBy = "commentEntity")
    private List<LikeEntity> likes;

    @Column(nullable = false, unique = false)
    private String content;

    @Column(nullable = false, unique = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, unique = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false, unique = false)
    private Boolean isDel;





}
