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

    @OneToMany(mappedBy = "commentEntity")
    private List<LikeEntity> likes;

    //    @Column(nullable = false) // 추후 추가
    @Column(name = "is_del", columnDefinition = "TINYINT(1) DEFAULT '0'")
    private Boolean isDel;


}
