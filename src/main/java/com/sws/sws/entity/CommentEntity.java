package com.sws.sws.entity;

import com.sws.sws.enums.PostTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CommentEntity extends PostTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int likes;

    //    @Column(nullable = false) // 추후 추가
    @Column(name = "is_del", columnDefinition = "TINYINT(1) DEFAULT '0'")
    private Boolean isDel;

    public CommentEntity updateComment(String content) {
        this.content = content;
        return this;
    }

    public int increaseLikesNums() {
        return this.likes += 1;
    }

    public int decreaseLikesNums() {
        return this.likes -= 1;
    }


}
