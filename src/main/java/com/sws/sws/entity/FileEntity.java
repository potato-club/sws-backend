package com.sws.sws.entity;

import com.sws.sws.enums.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    private PostEntity postEntity;

    //userEntity에 외래키에 넣기 그냥 userEntity와 매핑하기
    @Column(nullable = false, unique = true)
    private String s3Url;

    @Column(nullable = false, unique = false)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileType fileType;

    @Column(nullable = false, unique = false)
    private int size;


}
