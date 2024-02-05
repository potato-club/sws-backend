package com.sws.sws.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // 부모 삭제시 자식도 삭제
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent")
    private final List<CategoryEntity> child = new ArrayList<>();

    @OneToMany(mappedBy = "childCategory",cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PostEntity> posts = new ArrayList<>();

}
