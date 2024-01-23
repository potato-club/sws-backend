package com.sws.sws.entity;

import com.sws.sws.enums.TagName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TagName tagName;

    @OneToMany(mappedBy = "tagEntity")
    private List<UserTagEntity> userTags;

    @OneToMany(mappedBy = "tagEntity")
    private List<PostTagEntity> postTags;


}
