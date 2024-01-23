package com.sws.sws.entity;

import com.sws.sws.enums.TagName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    private PostEntity postEntity;

    @ManyToOne
    @JoinColumn(name="tag_id")
    private TagEntity tagEntity;
}
