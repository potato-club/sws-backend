package com.sws.sws.entity;

import com.sws.sws.enums.FileType;
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

    //userEntity에 외래키에 넣기 그냥 userEntity와 매핑하기
    //ungique=true 속성 삭제함
    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false, unique = false)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileType fileType;

    @Column(nullable = false, unique = false)
    private long size; //int->long으로 변경함

    //파일 삭제 여부 추가
    @Column(nullable = false)
    private boolean  deleted;


}
