package com.sws.sws.repository;

import com.sws.sws.entity.CategoryEntity;
import com.sws.sws.enums.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(CategoryName categoryName);

}
