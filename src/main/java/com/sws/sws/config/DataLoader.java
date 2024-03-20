//package com.sws.sws.config;
//
//import com.sws.sws.entity.CategoryEntity;
//import com.sws.sws.entity.TagEntity;
//import com.sws.sws.enums.CategoryName;
//import com.sws.sws.enums.TagName;
//import com.sws.sws.repository.CategoryRepository;
//import com.sws.sws.repository.TagRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//
//    private final CategoryRepository categoryRepository;
//    private final TagRepository tagRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        initializeCategories();
//        initializeTags();
//    }
//
//    // 카테고리 초기 enum타입들 데이터베이스에 삽입!
//    private void initializeCategories() {
//        for (CategoryName categoryName : CategoryName.values()) {
//            CategoryEntity categoryEntity = CategoryEntity.builder()
//                    .name(categoryName)
//                    .build();
//            categoryRepository.save(categoryEntity);
//        }
//    }
//
//        private void initializeTags() {
//        for (TagName tag : TagName.values()) {
//            TagEntity tagEntity = TagEntity.builder()
//                    .tag(tag)
//                    .build();
//            tagRepository.save(tagEntity);
//        }
//    }
//}
