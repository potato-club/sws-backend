package com.sws.sws.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity commentEntity;

    @Column(nullable = false, unique = false)
    private Boolean unLike;

    @Column(nullable = false, unique = false)
    private Boolean isDel;


}
