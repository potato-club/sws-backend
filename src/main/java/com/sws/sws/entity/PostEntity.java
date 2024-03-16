package com.sws.sws.entity;

import com.sws.sws.enums.PostTime;
import com.sws.sws.enums.TagName;
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
    private UserEntity userEntity;

    @OneToMany(mappedBy = "postEntity")
    private List<CommentEntity> comments;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likes;

    @Column(columnDefinition = "integer default 0", nullable = false) // 기본 조회수는 0
    private int views;

    @OneToMany(mappedBy = "postEntity")
    private List<FileEntity> files;

    @ElementCollection(targetClass = TagName.class)
    @Enumerated(EnumType.STRING)
    private List<TagName> postTags;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(nullable = false, unique = false)
    private String title;

    @Column(nullable = false, unique = false)
    private String content;

    @Column
    private Boolean isDel;


    public PostEntity updatePost(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public int increaseLikesNums() {
        return this.likes += 1;
    }

    public int decreaseLikesNums() {
        return this.likes -= 1;
    }

    public int increaseViews() {
        return this.views += 1;
    }


}
