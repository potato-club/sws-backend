package com.sws.sws.repository;

import com.sws.sws.entity.CategoryEntity;
import com.sws.sws.enums.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(CategoryName categoryName);
}
