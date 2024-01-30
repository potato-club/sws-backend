package com.sws.sws.repository;

import com.sws.sws.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);
}
