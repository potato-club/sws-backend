package com.sws.sws.repository;

import com.sws.sws.entity.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
    Optional<LibraryEntity> findByLibraryName(String libraryName);
}
