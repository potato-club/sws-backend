package com.sws.sws.entity;

import com.sws.sws.enums.PostTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity extends PostTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "postEntity")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "postEntity")
    private List<LikeEntity> likes;

    @OneToMany(mappedBy = "postEntity")
    private List<FileEntity> files;

    @OneToMany(mappedBy = "postEntity")
    private List<PostTagEntity> postTags;

    @ManyToOne
    private CategoryEntity category;

    @Column(nullable = false, unique = false)
    private String title;

    @Column(nullable = false, unique = false)
    private String content;

//    @Column(nullable = false, unique = false)
    @Column
    private int views;

//    @Column(nullable = false, unique = false)
    @Column
    private Boolean isDel;

    public PostEntity updatePost(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

}
