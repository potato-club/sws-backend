package com.sws.sws.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)  // 게시글 삭제 시 파일도 삭제
    @JoinColumn(name="post_id")
    private PostEntity postEntity;

    @OneToOne(fetch = FetchType.LAZY) //파일 올린 사용자
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    //ungique=true 속성 삭제함
    @Column(nullable = false, length = 512, columnDefinition = "VARCHAR(512) DEFAULT 'default_value_here'")
    private String fileUrl;

    @Column(nullable = false, unique = false)
    private String fileName;

    @Column(nullable = false, unique = false)
    private long size; //int->long으로 변경함

    //파일 삭제 여부 추가
    @Column(nullable = false)
    private boolean  deleted;

}
